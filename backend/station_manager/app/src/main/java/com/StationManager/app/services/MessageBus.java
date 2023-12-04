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

    private static final Map<
        Class<? extends Event>,
        List<BiFunction<Event, UnitOfWork, Void>>
    > eventHandlers = new HashMap<>();
    private static final Map<
        Class<? extends Command>,
        BiFunction<Command, UnitOfWork, Void>
    > commandHandlers = new HashMap<>();

    public static void addEventHandlers(
        Class<? extends Event> event,
        List<BiFunction<Event, UnitOfWork, Void>> handlers
    ) {
        eventHandlers.put(event, handlers);
    }

    public static void addCommandHandler(
        Class<? extends Command> command,
        BiFunction<Command, UnitOfWork, Void> handler
    ) {
        commandHandlers.put(command, handler);
    }

    public static void handle(Message message, UnitOfWork uow) {
        var messageQueue = new LinkedList<Message>();
        messageQueue.add(message);

        while (!messageQueue.isEmpty()) {
            message = messageQueue.poll();

            if (message instanceof Event event) {
                handleEvent(event, uow, messageQueue);
            } else if (message instanceof Command command) {
                handleCommand(command, uow, messageQueue);
            }
        }
    }

    public static void handleEvent(Event event, UnitOfWork uow, List<Message> queue) {
        logger.debug("Handling event " + event.getClass());
        for (var handler: eventHandlers.get(event.getClass())) {
            try {
                handler.apply(event, uow);
                queue.addAll(uow.collectNewEvents());
            } catch (Exception e) {
                logger.error(String.format("Exception when handling event: %s", e));
            }
        }
    }

    public static void handleCommand(Command command, UnitOfWork uow, List<Message> queue) {
        logger.debug("Handling command " + command.getClass());
        try {
            var handler = commandHandlers.get(command.getClass());
            handler.apply(command, uow);
            queue.addAll(uow.collectNewEvents());
        } catch (Exception e) {
            logger.error(String.format("Exception when handling event: %s", e));
            throw e;
        }
    }
}
