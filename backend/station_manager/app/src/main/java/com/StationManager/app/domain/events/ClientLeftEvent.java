package com.StationManager.app.domain.events;

import com.StationManager.app.domain.client.Client;

public class ClientLeftEvent extends Event {
    public Client client;

    public ClientLeftEvent(Client client) {
        super();
        this.client = client;
    }
}
