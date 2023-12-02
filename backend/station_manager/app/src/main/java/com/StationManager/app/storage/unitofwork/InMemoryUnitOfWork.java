package com.StationManager.app.storage.unitofwork;

import com.StationManager.app.storage.repository.*;
import com.StationManager.app.storage.repository.inmemory.*;

public class InMemoryUnitOfWork extends UnitOfWork {

    private IClientRepository clientRepository;
    private IHallRepository hallRepository;
    private IPrivilegyRepository privilegyRepository;
    private ITicketOfficeRepository ticketOfficeRepository;
    private ITrainStationRepository trainStationRepository;

    public InMemoryUnitOfWork() {
        clientRepository = new InMemoryClientRepository();
        hallRepository = new InMemoryHallRepository();
        privilegyRepository = new InMemoryPrivilegyRepository();
        ticketOfficeRepository = new InMemoryTicketOfficeRepository();
        trainStationRepository = new InMemoryTrainStationRepository();
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
