package com.StationManager.app.services.unitofwork;

import com.StationManager.app.domain.events.Event;
import com.StationManager.app.storage.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public abstract class UnitOfWork implements IUnitOfWork {
    protected static final Logger logger = LoggerFactory.getLogger(UnitOfWork.class);

    protected IClientRepository clientRepository;
    protected IHallRepository hallRepository;
    protected IPrivilegyRepository privilegyRepository;
    protected ITicketOfficeRepository ticketOfficeRepository;
    protected ITrainStationRepository trainStationRepository;

    @Override
    public List<Event> collectNewEvents() {
        var events = new ArrayList<Event>();
        for (var hall: this.getHallRepository().getSeen()) {
            while (!hall.events.isEmpty()) {
                events.add(hall.events.poll());
            }
        }
        for (var ticketOffice: this.getTicketOfficeRepository().getSeen()) {
            while (!ticketOffice.events.isEmpty()) {
                events.add(ticketOffice.events.poll());
            }
        }
        return events.stream().sorted().toList();
    }

    public IClientRepository getClientRepository() { return this.clientRepository; }
    public IHallRepository getHallRepository() { return this.hallRepository; }
    public IPrivilegyRepository getPrivilegyRepository() { return this.privilegyRepository; }
    public ITicketOfficeRepository getTicketOfficeRepository() { return this.ticketOfficeRepository; }
    public ITrainStationRepository getTrainStationRepository() { return this.trainStationRepository; }
}
