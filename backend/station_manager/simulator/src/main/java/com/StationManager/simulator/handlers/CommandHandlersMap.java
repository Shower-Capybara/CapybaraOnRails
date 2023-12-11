package com.StationManager.simulator.handlers;

import com.StationManager.shared.domain.commands.Command;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class CommandHandlersMap {
    private final Map<Class<? extends Command>, Object> map = new HashMap<>();
    public <T extends Command> void put(Class<T> key, Consumer<T> value) {
        map.put(key, value);
    }

    @SuppressWarnings("unchecked")
    public <T extends Command> Consumer<T> get(T command) {
        return (Consumer<T>) map.getOrDefault(command.getClass(), null);
    }
}