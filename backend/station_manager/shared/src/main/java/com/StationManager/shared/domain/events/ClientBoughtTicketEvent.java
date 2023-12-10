package com.StationManager.shared.domain.events;

import com.StationManager.shared.domain.client.Client;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ClientBoughtTicketEvent extends Event {
    public Client client;

    @JsonCreator
    public ClientBoughtTicketEvent(
        @JsonProperty(value = "client", required = true) Client client
    ) {
        this.client = client;
    }
}