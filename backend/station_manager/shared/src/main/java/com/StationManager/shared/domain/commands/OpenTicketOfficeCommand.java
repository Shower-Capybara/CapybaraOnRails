package com.StationManager.shared.domain.commands;

import com.StationManager.shared.domain.train_station.TicketOffice;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OpenTicketOfficeCommand extends Command {
    public Integer ticketOfficeId;

    @JsonCreator
    public OpenTicketOfficeCommand(
        @JsonProperty(value = "ticketOfficeId", required = true) Integer ticketOfficeId
    ) {
        this.ticketOfficeId = ticketOfficeId;
    }
}