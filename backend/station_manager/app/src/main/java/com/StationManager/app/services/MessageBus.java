package com.StationManager.app.services;

import com.StationManager.app.domain.Message;
import com.StationManager.app.domain.commands.Command;
import com.StationManager.app.domain.events.Event;
import com.StationManager.app.storage.unitofwork.UnitOfWork;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.function.BiFunction;

public class MessageBus {
    protected static final Logger logger = LoggerFactory.getLogger(MessageBus.class);

    public final UnitOfWork uow;
    private final Map<Class<? extends Event>, List<BiFunction<Event, UnitOfWork, Void>>> eventHandlers;
    private final Map<Class<? extends Command>, BiFunction<Command, UnitOfWork, Void>> commandHandlers;
    private final Queue<Message> messageQueue = new LinkedList<>();

    public MessageBus(
        UnitOfWork uow,
        Map<Class<? extends Event>, List<BiFunction<Event, UnitOfWork, Void>>> eventHandlers,
        Map<Class<? extends Command>, BiFunction<Command, UnitOfWork, Void>> commandHandlers
    ) {
        this.uow = uow;
        this.eventHandlers = eventHandlers;
        this.commandHandlers = commandHandlers;
    }

    public void handle(Message message) {
        this.messageQueue.add(message);

        while (!this.messageQueue.isEmpty()) {
            message = this.messageQueue.poll();

            if (message instanceof Event) {
                this.handleEvent((Event) message);
            } else if (message instanceof Command) {
                this.handleCommand((Command) message);
            }
        }
    }

    public void handleEvent(Event event) {
        logger.debug(String.format("Handling event %s", event.getClass()));
        for (var handler: this.eventHandlers.get(event.getClass())) {
            try {
                handler.apply(event, this.uow);
                // TODO: extend queue with uow.events
            } catch (Exception e) {
                logger.error(String.format("Exception when handling event: %s", e));
            }
        }
    }

    public void handleCommand(Command command) {
        logger.debug(String.format("Handling command %s", command.getClass()));
        try {
            var handler = this.commandHandlers.get(command.getClass());
            handler.apply(command, uow);
            // TODO: extend queue with uow.events
        } catch (Exception e) {
            logger.error(String.format("Exception when handling event: %s", e));
            throw e;
        }
    }
}
