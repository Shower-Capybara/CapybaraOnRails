package com.StationManager.shared.domain.events;

import com.StationManager.shared.domain.client.Client;
import com.StationManager.shared.domain.train_station.Hall;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonAppend;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ClientAddedEvent extends Event {
    public Hall hall;
    public Client client;

    @JsonCreator
    public ClientAddedEvent(
        @JsonProperty(value = "hall", required = true) Hall hall,
        @JsonProperty(value = "client", required = true) Client client
    ) {
        super();
        this.hall = hall;
        this.client = client;
    }
}
