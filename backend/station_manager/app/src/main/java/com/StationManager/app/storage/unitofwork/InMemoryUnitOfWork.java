package com.StationManager.app.storage.unitofwork;

import com.StationManager.app.domain.events.Event;
import com.StationManager.app.storage.repository.*;
import com.StationManager.app.storage.repository.inmemory.*;

import java.util.ArrayList;
import java.util.List;

public class InMemoryUnitOfWork extends UnitOfWork {
    public final IClientRepository clientRepository;
    public final IHallRepository hallRepository;
    public final IPrivilegyRepository privilegyRepository;
    public final ITicketOfficeRepository ticketOfficeRepository;
    public final ITrainStationRepository trainStationRepository;

    public InMemoryUnitOfWork() {
        this.clientRepository = new InMemoryClientRepository();
        this.hallRepository = new InMemoryHallRepository();
        this.privilegyRepository = new InMemoryPrivilegyRepository();
        this.ticketOfficeRepository = new InMemoryTicketOfficeRepository();
        this.trainStationRepository = new InMemoryTrainStationRepository();
    }

    @Override
    public List<Event> collectNewEvents() {
        var events = new ArrayList<Event>();
        for (var hall: hallRepository.getSeen()) {
            while (!hall.events.isEmpty()) {
                events.add(hall.events.poll());
            }
        }
        for (var ticketOffice: ticketOfficeRepository.getSeen()) {
            while (!ticketOffice.events.isEmpty()) {
                events.add(ticketOffice.events.poll());
            }
        }
        return events.stream().sorted().toList();
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
