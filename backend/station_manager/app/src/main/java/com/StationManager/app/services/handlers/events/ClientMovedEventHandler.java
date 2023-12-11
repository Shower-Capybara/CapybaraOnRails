package com.StationManager.app.services.handlers.events;

import com.StationManager.shared.domain.events.ClientMovedEvent;
import com.StationManager.shared.services.unitofwork.UnitOfWork;

public class ClientMovedEventHandler implements EventHandler<ClientMovedEvent> {
    @Override
    public void handle(ClientMovedEvent event, UnitOfWork uow) {
        var client = event.client;
        var newPoint = event.newPoint;
        uow.getClientRepository().updatePosition(client.getId(), newPoint);
    }
}

