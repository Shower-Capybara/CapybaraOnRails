package com.StationManager.shared.domain.commands;

import com.StationManager.shared.domain.client.Client;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ReassignClientCommand extends Command {
    public Client client;
    public Integer hallId;
    public Integer ticketOfficeId;


    @JsonCreator
    public ReassignClientCommand(
        @JsonProperty(value = "client", required = true) Client client,
        @JsonProperty(value = "hallId", required = true) Integer hallId,
        @JsonProperty(value = "ticketOfficeId", required = true) Integer ticketOfficeId
        ) {
        this.client = client;
        this.hallId = hallId;
        this.ticketOfficeId = ticketOfficeId;
    }
}