package com.StationManager.shared.domain.commands;

import com.StationManager.shared.domain.train_station.TicketOffice;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AddTicketOfficeCommand extends Command {
    public TicketOffice ticketOffice;
    public Integer hallId;

    @JsonCreator
    public AddTicketOfficeCommand(
        @JsonProperty(value = "ticketOffice", required = true) TicketOffice ticketOffice,
        @JsonProperty(value = "hallId", required = true) Integer hallId
    ) {
        this.ticketOffice = ticketOffice;
        this.hallId = hallId;
    }
}
