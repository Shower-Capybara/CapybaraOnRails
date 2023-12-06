package com.StationManager.app.domain.events;

import com.StationManager.app.domain.client.Client;

import java.awt.Point;

public class ClientMovedEvent extends Event {
    public Client client;
    public Point newPoint;

    public ClientMovedEvent(Client client, Point newPoint) {
        super();
        this.client = client;
        this.newPoint = newPoint;

    }
}

