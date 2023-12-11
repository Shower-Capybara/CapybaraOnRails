package com.StationManager.shared.domain.commands;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CloseTicketOfficeCommand extends Command {
    public Integer ticketOfficeId;

    @JsonCreator
    public CloseTicketOfficeCommand(
        @JsonProperty(value = "ticketOffice", required = true) Integer ticketOfficeId
    ) {
        this.ticketOfficeId = ticketOfficeId;
    }
}