package com.StationManager.shared.domain.events;

import com.StationManager.shared.domain.client.Client;

import java.awt.*;

public class ClientMovedEvent extends Event {
    public Client client;
    public Point previousPoint;
    public Point newPoint;

    public ClientMovedEvent(Client client, Point previousPoint, Point newPoint) {
        super();
        this.client = client;
        this.previousPoint = previousPoint;
        this.newPoint = newPoint;
    }
}

