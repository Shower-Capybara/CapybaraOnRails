package com.StationManager.app.domain.events;

import com.StationManager.app.domain.Message;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;

public abstract class Event implements Message, Comparable<Event> {
    protected LocalDateTime timestamp;
    protected Event() {
        this.timestamp = LocalDateTime.now();
    }

    public LocalDateTime getTimestamp() {
        return this.timestamp;
    }

    @Override
    public int compareTo(@NotNull Event o) {
        return this.timestamp.compareTo(o.timestamp);
    }
}


