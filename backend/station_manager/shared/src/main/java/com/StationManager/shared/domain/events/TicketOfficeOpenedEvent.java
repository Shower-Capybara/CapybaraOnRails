package com.StationManager.shared.domain.events;

import com.StationManager.shared.domain.train_station.TicketOffice;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TicketOfficeOpenedEvent extends Event {
    public Integer ticketOfficeId;

    @JsonCreator
    public TicketOfficeOpenedEvent(
        @JsonProperty(value = "ticketOfficeId", required = true) Integer ticketOfficeId
    ) {
        super();
        this.ticketOfficeId = ticketOfficeId;
    }
}