package com.StationManager.app.services;

import com.StationManager.app.domain.Message;
import com.StationManager.app.domain.commands.Command;
import com.StationManager.app.domain.events.Event;
import com.StationManager.app.services.handlers.commands.CommandHandler;
import com.StationManager.app.services.handlers.commands.CommandHandlersMap;
import com.StationManager.app.services.handlers.events.EventHandler;
import com.StationManager.app.services.handlers.events.EventHandlersMap;
import com.StationManager.app.services.unitofwork.UnitOfWork;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class MessageBus {
    protected static final Logger logger = LoggerFactory.getLogger(MessageBus.class);

    private static final EventHandlersMap eventHandlers = new EventHandlersMap();
    private static final CommandHandlersMap commandHandlers = new CommandHandlersMap();

    public static <T extends Event> void addEventHandlers(
        Class<T> event,
        List<EventHandler<T>> handlers
    ) {
        eventHandlers.put(event, handlers);
    }

    public static <T extends Command> void addCommandHandler(
        Class<T> command,
        CommandHandler<T> handler
    ) {
        commandHandlers.put(command, handler);
    }

    public static List<Event> handle(Message message, UnitOfWork uow) {
        var eventsList = new ArrayList<Event>();
        var messageQueue = new LinkedList<Message>();
        messageQueue.add(message);

        while (!messageQueue.isEmpty()) {
            message = messageQueue.poll();

            if (message instanceof Event event) {
                eventsList.add(event);
                handleEvent(event, uow, messageQueue);
            } else if (message instanceof Command command) {
                handleCommand(command, uow, messageQueue);
            }
        }
        return eventsList;
    }

    public static <T extends Event> void handleEvent(T event, UnitOfWork uow, List<Message> queue) {
        logger.debug("Handling event " + event.getClass());
        var handlers = eventHandlers.get(event);
        if (handlers == null) {
            logger.info("Handlers list is empty for event " + event.getClass());
            return;
        }

        for (var handler: handlers) {
            try {
                handler.handle(event, uow);
                queue.addAll(uow.collectNewEvents());
            } catch (Exception e) {
                logger.error(String.format("Exception when handling event: %s", e));
            }
        }
    }

    public static void handleCommand(Command command, UnitOfWork uow, List<Message> queue) {
        logger.debug("Handling command " + command.getClass());
        try {
            var handler = commandHandlers.get(command);
            if (handler == null) {
                logger.info("Handler is null for command " + command.getClass());
                return;
            }

            handler.handle(command, uow);
            queue.addAll(uow.collectNewEvents());
        } catch (Exception e) {
            logger.error(String.format("Exception when handling event: %s", e));
            throw e;
        }
    }
}
