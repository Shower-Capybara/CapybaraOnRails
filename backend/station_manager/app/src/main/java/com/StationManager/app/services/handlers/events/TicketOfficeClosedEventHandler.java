package com.StationManager.app.services.handlers.events;

import com.StationManager.shared.services.unitofwork.UnitOfWork;
import com.StationManager.shared.domain.events.TicketOfficeClosedEvent;

public class TicketOfficeClosedEventHandler implements EventHandler<TicketOfficeClosedEvent> {
    @Override
    public void handle(TicketOfficeClosedEvent event, UnitOfWork uow) {
        var ticketOfficeId = event.ticketOfficeId;
        uow.getTicketOfficeRepository().getById(ticketOfficeId).ifPresent(ticketOffice -> ticketOffice.setIsClosed(true));
    }
}
