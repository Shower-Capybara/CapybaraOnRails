package com.StationManager.app.domain.events;

import com.StationManager.app.domain.Message;

import java.time.LocalDateTime;

public abstract class Event implements Message {
    protected LocalDateTime timestamp;
    protected Event() {
        this.timestamp = LocalDateTime.now();
    }

    public LocalDateTime getTimestamp() {
        return this.timestamp;
    }
}


