package com.StationManager.app.services.handlers.events;

import com.StationManager.app.domain.events.Event;
import com.StationManager.app.services.unitofwork.UnitOfWork;

public interface EventHandler<T extends Event> {
    void handle(T event, UnitOfWork uow);
}