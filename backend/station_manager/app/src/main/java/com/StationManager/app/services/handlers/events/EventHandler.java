package com.StationManager.app.services.handlers.events;

import com.StationManager.shared.domain.events.Event;
import com.StationManager.shared.services.unitofwork.UnitOfWork;

public interface EventHandler<T extends Event> {
    void handle(T event, UnitOfWork uow);
}