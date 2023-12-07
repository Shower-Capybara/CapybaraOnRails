package com.StationManager.app.services.handlers.events;

import com.StationManager.app.domain.events.TicketOfficeAddedEvent;
import com.StationManager.app.services.unitofwork.UnitOfWork;

public class TicketOfficeAddedEventHandler implements EventHandler<TicketOfficeAddedEvent> {
    @Override
    public void handle(TicketOfficeAddedEvent event, UnitOfWork uow) {
        var ticketOffice = event.ticketOffice;
        uow.getTicketOfficeRepository().add(ticketOffice);
    }

}
