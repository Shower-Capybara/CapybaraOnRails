package com.StationManager.shared.domain.commands;

import com.StationManager.shared.domain.client.Client;

public class AddClientCommand extends Command {
    public Client client;
    public Integer hallId;

    public AddClientCommand(Client client, Integer hallId) {
        this.client = client;
        this.hallId = hallId;
    }
}