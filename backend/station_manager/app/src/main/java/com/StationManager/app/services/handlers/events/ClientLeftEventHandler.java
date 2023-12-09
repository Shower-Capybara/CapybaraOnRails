package com.StationManager.app.services.handlers.events;

import com.StationManager.shared.domain.events.ClientLeftEvent;
import com.StationManager.app.services.unitofwork.UnitOfWork;

public class ClientLeftEventHandler implements EventHandler<ClientLeftEvent> {
    @Override
    public void handle(ClientLeftEvent event, UnitOfWork uow) {
        var client = event.client;
        uow.getClientRepository().remove(client);
    }
}