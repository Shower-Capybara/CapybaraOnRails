package com.StationManager.app.services.handlers.events;

import com.StationManager.shared.domain.events.TicketOfficeAddedEvent;
import com.StationManager.shared.services.unitofwork.UnitOfWork;

public class TicketOfficeAddedEventHandler implements EventHandler<TicketOfficeAddedEvent> {
    @Override
    public void handle(TicketOfficeAddedEvent event, UnitOfWork uow) {

    }

}
