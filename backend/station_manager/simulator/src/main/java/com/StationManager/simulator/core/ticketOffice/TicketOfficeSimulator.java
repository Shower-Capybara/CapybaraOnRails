package com.StationManager.simulator.core.ticketOffice;

import com.StationManager.shared.domain.Message;
import com.StationManager.shared.domain.events.ClientBoughtTicketEvent;
import com.StationManager.shared.domain.events.ClientServedEvent;
import com.StationManager.shared.domain.events.Event;
import com.StationManager.shared.domain.events.ClientBeingServedEvent;
import com.StationManager.simulator.Json;
import com.StationManager.simulator.Settings;
import com.StationManager.simulator.handlers.EventHandlersMap;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

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
        var numTickets = 2; // add numTickets somewhere to the client
        for (int ticket = 1; ticket < numTickets + 1; ticket++) {
            try {
                Thread.sleep(3 * 1000); // simulate buying
            } catch (InterruptedException e) {
                break;
            }

            var newEvent = new ClientBoughtTicketEvent(event.client);
            try {
                var channel = Settings.getEventChannel(newEvent.getClass().getSimpleName());
                this.redis.publish(channel, this.objectMapper.writeValueAsString(newEvent));
            } catch (JsonProcessingException e) {
                logger.error(e.toString());
            }
        }

        var newEvent = new ClientServedEvent(event.ticketOffice.getId(), event.client);
        try {
            var channel = Settings.getEventChannel(newEvent.getClass().getSimpleName());
            this.redis.publish(channel, this.objectMapper.writeValueAsString(newEvent));
        } catch (JsonProcessingException e) {
            logger.error(e.toString());
        }
    }

    public <T extends Event> void handleEvent(T event) {
        var handlers = this.handlers.get(event);
        if (handlers == null) {
            logger.info("No handler found for " + event.getClass().getSimpleName() + " event");
            return;
        }

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
            } catch (InterruptedException | JsonProcessingException e) {
                break;
            }
        }
    }

    public Integer getId() {return this.id; }
}
