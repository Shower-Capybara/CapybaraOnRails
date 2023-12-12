package com.StationManager.app.services.handlers.events;

import com.StationManager.shared.domain.events.EntranceAddedEvent;
import com.StationManager.shared.services.unitofwork.UnitOfWork;

public class EntranceAddedEventHandler implements EventHandler<EntranceAddedEvent> {
    @Override
    public void handle(EntranceAddedEvent event, UnitOfWork uow) {
    }
}