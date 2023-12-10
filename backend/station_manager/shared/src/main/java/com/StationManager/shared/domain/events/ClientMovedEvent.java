package com.StationManager.shared.domain.events;

import com.StationManager.shared.domain.client.Client;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.awt.*;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ClientMovedEvent extends Event {
    public Client client;
    public Point previousPoint;
    public Point newPoint;

    @JsonCreator
    public ClientMovedEvent(
        @JsonProperty(value = "client", required = true) Client client,
        @JsonProperty(value = "previousPoint", required = true) Point previousPoint,
        @JsonProperty(value = "newPoin", required = true) Point newPoint
    ) {
        super();
        this.client = client;
        this.previousPoint = previousPoint;
        this.newPoint = newPoint;
    }
}

