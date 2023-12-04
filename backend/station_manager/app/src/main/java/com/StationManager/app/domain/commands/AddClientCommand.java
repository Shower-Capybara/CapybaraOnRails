package com.StationManager.app.domain.commands;

import com.StationManager.app.domain.client.Client;

public record AddClientCommand(Client client) implements Command { }