package com.StationManager.app.services.unitofwork;

import com.StationManager.app.storage.repository.inmemory.*;

public class InMemoryUnitOfWork extends UnitOfWork {
    public InMemoryUnitOfWork() {
        this.clientRepository = new InMemoryClientRepository();
        this.hallRepository = new InMemoryHallRepository();
        this.privilegyRepository = new InMemoryPrivilegyRepository();
        this.ticketOfficeRepository = new InMemoryTicketOfficeRepository();
        this.trainStationRepository = new InMemoryTrainStationRepository();
    }

    @Override
    public void commit() {
        // not required for in memory storage
        logger.info("Changes committed.");
    }

    @Override
    public void rollback() {
        // not required for in memory storage
        logger.info("Changes rollback.");
    }

    @Override
    public void close() throws Exception {
        this.rollback();
    }
}
