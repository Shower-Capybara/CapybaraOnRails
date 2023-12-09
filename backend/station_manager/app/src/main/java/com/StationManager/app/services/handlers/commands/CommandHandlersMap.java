package com.StationManager.app.services.handlers.commands;

import com.StationManager.shared.domain.commands.Command;

import java.util.HashMap;
import java.util.Map;

public class CommandHandlersMap {
    private final Map<Class<? extends Command>, Object> map = new HashMap<>();
    public <T extends Command> void put(Class<T> key, CommandHandler<T> value) {
        map.put(key, value);
    }

    @SuppressWarnings("unchecked")
    public <T extends Command> CommandHandler<T> get(T command) {
        return (CommandHandler<T>) map.getOrDefault(command.getClass(), null);
    }
}