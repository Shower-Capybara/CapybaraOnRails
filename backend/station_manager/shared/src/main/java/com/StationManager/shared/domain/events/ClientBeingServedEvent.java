package com.StationManager.shared.domain.events;

import com.StationManager.shared.domain.client.Client;
import com.StationManager.shared.domain.train_station.TicketOffice;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ClientBeingServedEvent extends Event {
    public TicketOffice ticketOffice;
    public Client client;

    @JsonCreator
    public ClientBeingServedEvent(
        @JsonProperty(value = "ticketOffice", required = true) TicketOffice ticketOffice,
        @JsonProperty(value = "client", required = true) Client client
    ) {
        this.ticketOffice = ticketOffice;
        this.client = client;
    }

}
