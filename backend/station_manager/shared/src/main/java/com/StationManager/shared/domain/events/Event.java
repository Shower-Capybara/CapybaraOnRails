package com.StationManager.shared.domain.events;

import com.StationManager.shared.domain.Message;
import com.fasterxml.jackson.annotation.JsonProperty;
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
    @JsonProperty("type")
    private String type() {
        return this.getClass().getSimpleName();
    }

    @Override
    public int compareTo(@NotNull Event o) {
        return this.timestamp.compareTo(o.timestamp);
    }
}


