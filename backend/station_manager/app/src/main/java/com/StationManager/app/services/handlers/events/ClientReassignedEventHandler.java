package com.StationManager.app.services.handlers.events;

import com.StationManager.shared.domain.events.ClientReassignedEvent;
import com.StationManager.shared.services.unitofwork.UnitOfWork;

public class ClientReassignedEventHandler implements EventHandler<ClientReassignedEvent> {
    @Override
    public void handle(ClientReassignedEvent event, UnitOfWork uow) {

    }
}
