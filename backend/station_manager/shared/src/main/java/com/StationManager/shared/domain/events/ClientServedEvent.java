package com.StationManager.shared.domain.events;

import com.StationManager.shared.domain.client.Client;
import com.StationManager.shared.domain.train_station.Hall;
import com.StationManager.shared.domain.train_station.TicketOffice;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ClientServedEvent extends Event {
    public Integer ticketOfficeId;
    public Client client;

    @JsonCreator
    public ClientServedEvent(
        @JsonProperty(value = "ticketOfficeId", required = true) Integer ticketOfficeId,
        @JsonProperty(value = "client", required = true) Client client
    ) {
        this.ticketOfficeId = ticketOfficeId;
        this.client = client;
    }
}