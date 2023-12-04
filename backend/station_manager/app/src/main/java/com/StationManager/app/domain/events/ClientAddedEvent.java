package com.StationManager.app.domain.events;

import com.StationManager.app.domain.client.Client;

public record ClientAddedEvent(Client client) implements Event { }
