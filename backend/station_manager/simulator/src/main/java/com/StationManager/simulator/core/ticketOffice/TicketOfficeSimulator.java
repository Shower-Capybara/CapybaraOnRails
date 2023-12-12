package com.StationManager.simulator.core.ticketOffice;

import com.StationManager.simulator.handlers.CommandHandlersMap;
import com.StationManager.shared.domain.Message;
import com.StationManager.shared.domain.commands.CloseTicketOfficeCommand;
import com.StationManager.shared.domain.commands.Command;
import com.StationManager.shared.domain.commands.OpenTicketOfficeCommand;
import com.StationManager.shared.domain.events.*;
import com.StationManager.simulator.Json;
import com.StationManager.simulator.Settings;
import com.StationManager.simulator.handlers.EventHandlersMap;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.Semaphore;

public class TicketOfficeSimulator implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(TicketOfficeSimulator.class);
    private Boolean isSuspended = false;

    private final Integer id;
    private final BlockingQueue<String> messageQueue = new LinkedBlockingQueue<>();
    private final Jedis redis;
    private final ObjectMapper objectMapper = Json.getObjectMapper();
    private final EventHandlersMap eventHandlers = new EventHandlersMap();
    private final CommandHandlersMap commandHandlers = new CommandHandlersMap();


    public TicketOfficeSimulator(Integer id, Jedis redis) {
        this.id = id;
        this.redis = redis;
        this.eventHandlers.put(ClientBeingServedEvent.class, List.of(this::handleClientBeingServedEvent));
        this.eventHandlers.put(ClientBeingServedEvent.class, List.of(this::handleClientBeingServedEvent));
        this.commandHandlers.put(CloseTicketOfficeCommand.class, this::handleCloseTicketOfficeCommand);
        this.commandHandlers.put(OpenTicketOfficeCommand.class, this::handleOpenTicketOfficeCommand);
    }

    public void publishMessage(String message) {
        this.messageQueue.add(message);
    }

    private void handleClientBeingServedEvent(ClientBeingServedEvent event) {
        if(isSuspended){
            return;
        }
        Timestamp clientServeStartTime = Timestamp.valueOf(LocalDateTime.now());
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
        Timestamp clientServeEndTime = Timestamp.valueOf(LocalDateTime.now());

        var newClientServedEvent = new ClientServedEvent(event.ticketOffice.getId(), event.client);
        var newLogRecordEvent = new LogRecordEvent(event.client, clientServeStartTime, clientServeEndTime);
        try {
            var clientServedChannel = Settings.getEventChannel(newClientServedEvent.getClass().getSimpleName());
            this.redis.publish(clientServedChannel, this.objectMapper.writeValueAsString(newClientServedEvent));
            var logRecordChannel = Settings.getEventChannel(newLogRecordEvent.getClass().getSimpleName());
            this.redis.publish(logRecordChannel, this.objectMapper.writeValueAsString(newLogRecordEvent));
        } catch (JsonProcessingException e) {
            logger.error(e.toString());
        }
    }

    private void handleCloseTicketOfficeCommand(CloseTicketOfficeCommand command)  {
        this.isSuspended = true;
        this.messageQueue.clear();
        var newEvent = new TicketOfficeClosedEvent(this.getId());
        try {
            var channel = Settings.getEventChannel(newEvent.getClass().getSimpleName());
            this.redis.publish(channel, this.objectMapper.writeValueAsString(newEvent));
        } catch (JsonProcessingException e) {
            logger.error(e.toString());
        }catch (Exception ex){
            logger.error("Event cant be sent!!!");
        }
    }
    private void handleOpenTicketOfficeCommand(OpenTicketOfficeCommand command) {
        this.isSuspended = false;
        var newEvent = new TicketOfficeOpenedEvent(this.getId());
        try {
            var channel = Settings.getEventChannel(newEvent.getClass().getSimpleName());
            this.redis.publish(channel, this.objectMapper.writeValueAsString(newEvent));
        } catch (JsonProcessingException e) {
            logger.error(e.toString());
        }
    }

    public <T extends Event> void handleEvent(T event) {
        var handlers = this.eventHandlers.get(event);
        if (handlers == null) {
            logger.info("No handler found for " + event.getClass().getSimpleName() + " event");
            return;
        }

        for (var handler : handlers) {
            handler.accept(event);
        }
    }

    public <T extends Command> void handleCommand(T command) {
        var handler = this.commandHandlers.get(command);
        if (handler == null) {
            logger.info("No handler found for " + command.getClass().getSimpleName() + " command");
            return;
        }
        handler.accept(command);
    }

    @Override
    public void run() {
        while (true) {
            try {
                var data = this.messageQueue.take();
                var message = objectMapper.readValue(data, Message.class);
                if (message instanceof Event event){
                    handleEvent(event);
                }
                else if (message instanceof Command command){
                    handleCommand(command);
                }
            } catch (InterruptedException | JsonProcessingException e) {
                break;
            }
        }
    }

    public Integer getId() {
        return this.id;
    }
}
