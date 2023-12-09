package com.StationManager.app.services.handlers.events;

import com.StationManager.shared.domain.events.Event;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EventHandlersMap {
    private final Map<Class<? extends Event>, Object> map = new HashMap<>();
    public <T extends Event> void put(Class<T> key, List<EventHandler<T>> value) {
        map.put(key, value);
    }

    @SuppressWarnings("unchecked")
    public <T extends Event> List<EventHandler<T>> get(T event) {
        return (List<EventHandler<T>>) map.getOrDefault(event.getClass(), null);
    }
}