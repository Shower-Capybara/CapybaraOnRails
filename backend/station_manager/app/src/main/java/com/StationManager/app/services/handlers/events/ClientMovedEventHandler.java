package com.StationManager.app.services.handlers.events;

import com.StationManager.app.domain.events.ClientMovedEvent;
import com.StationManager.app.services.unitofwork.UnitOfWork;

public class ClientMovedEventHandler implements EventHandler<ClientMovedEvent> {
    @Override
    public void handle(ClientMovedEvent event, UnitOfWork uow) {
        var client = event.client;
        var newPoint = event.newPoint;
        uow.getClientRepository().updatePosition(client.getId(), newPoint);
    }
}

