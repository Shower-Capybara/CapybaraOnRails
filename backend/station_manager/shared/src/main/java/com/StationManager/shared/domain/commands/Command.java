package com.StationManager.shared.domain.commands;

import com.StationManager.shared.domain.Message;

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
