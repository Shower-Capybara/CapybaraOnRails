package com.StationManager.app.services.command_listener;

import com.StationManager.app.Settings;
import com.StationManager.app.services.MessageBus;
import com.StationManager.app.services.unitofwork.InMemoryUnitOfWork;
import com.StationManager.shared.domain.commands.AddClientCommand;
import com.StationManager.shared.domain.train_station.Direction;
import com.StationManager.shared.domain.train_station.Hall;
import com.StationManager.shared.domain.train_station.Segment;
import com.StationManager.shared.domain.train_station.TicketOffice;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.Consumer;

public class RedisPubSub extends JedisPubSub {
    private static final Logger logger = LoggerFactory.getLogger(RedisPubSub.class);
    private final Gson gson = Json.getGson();
    private final Jedis redisPublisher;
    private final HashMap<String, Consumer<JsonObject>> handlers = new HashMap<>();

    public RedisPubSub(Jedis redisPublisher) {
        super();
        this.redisPublisher = redisPublisher;
        this.handlers.put("AddClientCommand", this::handleAddClientCommand);
    }

    private JsonObject getJsonObject(String data) {
        JsonElement je = gson.fromJson(data, JsonElement.class);
        return je.getAsJsonObject();
    }

    private void tryParseMessage(JsonObject obj) {
        JsonElement typeElement = obj.get("type");
        if (typeElement == null) {
            throw new IllegalArgumentException("Message format is invalid, no `type` field");
        }

        JsonElement payloadElement = obj.get("payload");
        if (payloadElement == null) {
            throw new IllegalArgumentException("Message format is invalid, no `payload` field");
        }
    }

    public void handleAddClientCommand(JsonElement data) {
        var command = gson.fromJson(data, AddClientCommand.class);
        if (command.client == null || command.hallId == null) {
            logger.error("Insufficient data for AddClientCommand command: " + data.toString());
            return;
        }
        try (var uow = new InMemoryUnitOfWork()) {
            var events = MessageBus.handle(command, uow);

            for (var event: events) {
                // TODO: move payload generation into the event itself
                HashMap<String, Object> message = new HashMap<>();
                message.put("type", event.getClass().getSimpleName());
                message.put("payload", event);
                redisPublisher.publish(Settings.REDIS_EVENTS_CHANNEL, gson.toJson(message));
            }
        } catch (Exception e) {
            logger.error(e.toString());
        }
    }

    @Override
    public void onMessage(String channel, String message) {
        logger.info(String.format("Channel %s received message: %s", channel, message));
        try {
            var obj = this.getJsonObject(message);
            this.tryParseMessage(obj);
            String type = obj.get("type").getAsString();
            var handler = this.handlers.get(type);
            if (handler == null) return;
            handler.accept(obj.get("payload").getAsJsonObject());
        } catch (Exception e) {
            logger.error(e.toString());
        }
    }
}
