package com.StationManager.shared.domain.events;

import com.StationManager.shared.domain.client.Client;
import com.StationManager.shared.domain.train_station.Hall;

public class ClientAddedEvent extends Event {
    public Hall hall;
    public Client client;

    public ClientAddedEvent(Hall hall, Client client) {
        super();
        this.hall = hall;
        this.client = client;
    }
}
