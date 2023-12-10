package com.StationManager.simulator.handlers;

import com.StationManager.shared.domain.events.Event;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class EventHandlersMap {
    private final Map<Class<? extends Event>, Object> map = new HashMap<>();
    public <T extends Event> void put(Class<T> key, List<Consumer<T>> value) {
        map.put(key, value);
    }

    @SuppressWarnings("unchecked")
    public <T extends Event> List<Consumer<T>> get(T event) {
        return (List<Consumer<T>>) map.getOrDefault(event.getClass(), null);
    }
}