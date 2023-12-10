package com.StationManager.simulator;

import com.StationManager.shared.domain.Message;
import com.StationManager.shared.domain.events.Event;
import com.StationManager.shared.domain.events.ClientBeingServedEvent;
import com.StationManager.simulator.handlers.EventHandlersMap;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.function.Consumer;

public class TicketOfficeSimulator implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(TicketOfficeSimulator.class);

    private final Integer id;
    private final BlockingQueue<String> messageQueue = new LinkedBlockingQueue<>();
    private final Jedis redis;
    private final ObjectMapper objectMapper = Json.getObjectMapper();
    private final EventHandlersMap handlers = new EventHandlersMap();

    public TicketOfficeSimulator(Integer id, Jedis redis) {
        this.id = id;
        this.redis = redis;
        this.handlers.put(
            ClientBeingServedEvent.class,
            List.of(this::handleClientBeingServedEvent)
        );
    }

    public void publishMessage(String message) {
        this.messageQueue.add(message);
    }

    private void handleClientBeingServedEvent(ClientBeingServedEvent event) {

    }

    public <T extends Event> void handleEvent(T event) {
        var handlers = this.handlers.get(event);
        if (handlers == null) {
            logger.info("No handler found for " + event.getClass().getSimpleName() + " event");
            return;
        };

        for (var handler: handlers) {
            handler.accept(event);
        }
    }

    @Override
    public void run() {
        while (true) {
            try {
                var data = this.messageQueue.take();
                var message = objectMapper.readValue(data, Message.class);

                if (message instanceof Event event) handleEvent(event);

//                redis.publish(Settings.REDIS_CHANNEL, "1"); // publish new client message
                logger.info("Publishing new client command");
            } catch (InterruptedException | JsonProcessingException e) {
                break;
            }
        }
    }

    public Integer getId() {return this.id; }
}
