package com.StationManager.app.domain.commands;

import com.StationManager.app.domain.Message;

import java.time.LocalDateTime;

public abstract class Command implements Message {
    protected LocalDateTime timestamp;
    protected Command() {
        this.timestamp = LocalDateTime.now();
    }

    public LocalDateTime getTimestamp() {
        return this.timestamp;
    }
}
