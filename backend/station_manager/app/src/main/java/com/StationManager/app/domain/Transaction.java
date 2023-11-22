package com.StationManager.app.domain;

import com.StationManager.app.domain.client.Client;

import java.time.LocalDateTime;

public class Transaction {
    public Client client;
    public LocalDateTime startedAt;
    public LocalDateTime endedAt;

    public Transaction(Client client, LocalDateTime startedAt) {
        this.client = client;
        this.startedAt = startedAt;
    }
}
