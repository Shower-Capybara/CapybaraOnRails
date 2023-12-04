package com.StationManager.app.storage.unitofwork;

import com.StationManager.app.domain.events.Event;

import java.util.List;

public interface IUnitOfWork extends AutoCloseable {
    List<Event> collectNewEvents();
    void commit();
    void rollback();
}
