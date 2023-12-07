package com.StationManager.app.domain.commands;

import com.StationManager.app.domain.client.Client;

public class AddClientCommand extends Command {
    public Client client;
    public Integer hallId;

    public AddClientCommand(Client client, Integer hallId) {
        this.client = client;
        this.hallId = hallId;
    }
}