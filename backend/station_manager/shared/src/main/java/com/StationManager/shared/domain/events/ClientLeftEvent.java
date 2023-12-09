package com.StationManager.shared.domain.events;

import com.StationManager.shared.domain.client.Client;

public class ClientLeftEvent extends Event {
    public Client client;

    public ClientLeftEvent(Client client) {
        super();
        this.client = client;
    }
}
