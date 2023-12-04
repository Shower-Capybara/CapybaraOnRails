package com.StationManager.app.domain.events;

import com.StationManager.app.domain.client.Client;

public class ClientAddedEvent extends Event {
    public Client client;

    public ClientAddedEvent(Client client) {
        super();
        this.client = client;
    }
}
