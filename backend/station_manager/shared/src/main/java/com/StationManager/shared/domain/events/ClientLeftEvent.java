package com.StationManager.shared.domain.events;

import com.StationManager.shared.domain.client.Client;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ClientLeftEvent extends Event {
    public Client client;

    @JsonCreator
    public ClientLeftEvent(
        @JsonProperty(value = "client", required = true) Client client
    ) {
        super();
        this.client = client;
    }
}
