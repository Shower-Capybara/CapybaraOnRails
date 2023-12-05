package com.StationManager.app.services.handlers.events;

import com.StationManager.app.domain.events.ClientAddedEvent;
import com.StationManager.app.services.unitofwork.UnitOfWork;

public class ClientAddedEventHandler implements EventHandler<ClientAddedEvent> {
    @Override
    public void handle(ClientAddedEvent event, UnitOfWork uow) {
        var client = event.client;
        event.hall.assignToTicketOffice(client);
        uow.getClientRepository().updatePosition(client.getId(), client.getPosition());
    }
}
