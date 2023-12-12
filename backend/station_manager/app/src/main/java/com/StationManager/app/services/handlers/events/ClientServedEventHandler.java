package com.StationManager.app.services.handlers.events;

import com.StationManager.shared.services.unitofwork.UnitOfWork;
import com.StationManager.shared.domain.events.ClientServedEvent;

public class ClientServedEventHandler implements EventHandler<ClientServedEvent> {
    @Override
    public void handle(ClientServedEvent event, UnitOfWork uow) {
        var ticketOfficeResult = uow.getTicketOfficeRepository().getById(event.ticketOfficeId);
        if (ticketOfficeResult.isEmpty()) {
            throw new IllegalArgumentException("No ticket office match given id");
        }
        ticketOfficeResult.get().removeClient();
    }
}
