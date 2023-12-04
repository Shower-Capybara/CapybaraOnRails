package com.StationManager.app.domain.commands;

import com.StationManager.app.domain.client.Client;

public class AddClientCommand extends Command {
    public Client client;

    public AddClientCommand(Client client) {
        this.client = client;
    }
}