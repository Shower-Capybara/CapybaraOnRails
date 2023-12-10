package com.StationManager.simulator;

import com.StationManager.shared.domain.events.ClientBeingServedEvent;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;

import java.util.HashMap;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.function.Consumer;

public class TicketOfficeSimulator implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(TicketOfficeSimulator.class);

    private final Integer id;
    private final BlockingQueue<String> messageQueue = new LinkedBlockingQueue<>();
    private final Jedis redis;
    private final Gson gson = Json.getGson();
    private final HashMap<String, Consumer<JsonObject>> handlers = new HashMap<>();

    public TicketOfficeSimulator(Integer id, Jedis redis) {
        this.id = id;
        this.redis = redis;
        this.handlers.put("ClientBeingServedEvent", this::handleClientBeingServedEvent);
    }

    public void publishMessage(String message) {
        this.messageQueue.add(message);
    }

    private JsonObject getJsonObject(String data) {
        JsonElement je = gson.fromJson(data, JsonElement.class);
        return je.getAsJsonObject();
    }

    private void handleClientBeingServedEvent(JsonObject data) {
        var payload = gson.fromJson(data, ClientBeingServedEvent.class);

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

    @Override
    public void run() {
        while (true) {
            try {
                var message = this.messageQueue.take();
                JsonObject obj;

                try {
                    obj = getJsonObject(message);
                    tryParseMessage(obj);
                } catch (IllegalArgumentException | IllegalStateException e) {
                    logger.error(e.toString());
                    continue;
                }

                String type = obj.get("type").getAsString();
                var handler = this.handlers.get(type);
                if (handler == null) {
                    logger.info("No handler found for " + type + " event");
                    continue;
                };

                handler.accept(obj.get("payload").getAsJsonObject());
//                redis.publish(Settings.REDIS_CHANNEL, "1"); // publish new client message
                logger.info("Publishing new client command");
            } catch (InterruptedException e) {
                break;
            }
        }
    }

    public Integer getId() {return this.id; }
}
