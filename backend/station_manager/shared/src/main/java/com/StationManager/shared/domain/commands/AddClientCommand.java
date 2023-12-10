package com.StationManager.shared.domain.commands;

import com.StationManager.shared.domain.client.Client;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AddClientCommand extends Command {
    public Client client;
    public Integer hallId;

    @JsonCreator
    public AddClientCommand(
        @JsonProperty(value = "client", required = true) Client client,
        @JsonProperty(value = "hallId", required = true) Integer hallId
    ) {
        this.client = client;
        this.hallId = hallId;
    }
}