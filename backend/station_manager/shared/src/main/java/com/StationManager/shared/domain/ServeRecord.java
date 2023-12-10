package com.StationManager.shared.domain;

import com.StationManager.shared.domain.client.Client;

import java.time.LocalDateTime;

public class ServeRecord {
    public Client client;
    public LocalDateTime startedAt;
    public LocalDateTime endedAt;

    public ServeRecord(Client client, LocalDateTime startedAt) {
        this.client = client;
        this.startedAt = startedAt;
    }
}
