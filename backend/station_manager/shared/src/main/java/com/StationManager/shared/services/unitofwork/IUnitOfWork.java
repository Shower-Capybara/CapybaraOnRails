package com.StationManager.shared.services.unitofwork;

import com.StationManager.shared.domain.events.Event;
import com.StationManager.shared.storage.repository.*;

import java.util.List;

public interface IUnitOfWork extends AutoCloseable {
    List<Event> collectNewEvents();
    void commit();
    void rollback();

    IClientRepository getClientRepository();
    IHallRepository getHallRepository();
    IPrivilegyRepository getPrivilegyRepository();
    ITicketOfficeRepository getTicketOfficeRepository();
    ITrainStationRepository getTrainStationRepository();
}
