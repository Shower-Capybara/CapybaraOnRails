package com.StationManager.app.services.handlers.events;

import com.StationManager.app.services.unitofwork.UnitOfWork;
import com.StationManager.shared.domain.events.TicketOfficeClosedEvent;
import com.StationManager.shared.domain.events.TicketOfficeOpenedEvent;

public class TicketOfficeOpenedEventHandler implements EventHandler<TicketOfficeOpenedEvent> {
    @Override
    public void handle(TicketOfficeOpenedEvent event, UnitOfWork uow) {
        var ticketOfficeId = event.ticketOfficeId;
        uow.getTicketOfficeRepository().getById(ticketOfficeId).ifPresent(ticketOffice -> ticketOffice.setClosed(false));
    }

}
