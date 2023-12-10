package com.StationManager.shared.domain.events;

import com.StationManager.shared.domain.train_station.TicketOffice;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TicketOfficeAddedEvent extends Event {
    public TicketOffice ticketOffice;

    @JsonCreator
    public TicketOfficeAddedEvent(
        @JsonProperty(value = "ticketOffice", required = true) TicketOffice ticketOffice
    ) {
        super();
        this.ticketOffice = ticketOffice;
    }
}
