package com.StationManager.app.storage.unitofwork;

import com.StationManager.app.domain.client.*;
import com.StationManager.app.domain.events.Event;
import com.StationManager.app.domain.train_station.Hall;
import com.StationManager.app.domain.train_station.TicketOffice;
import com.StationManager.app.domain.train_station.TrainStation;
import com.StationManager.app.storage.repository.*;
import com.StationManager.app.storage.repository.inmemory.*;
import com.StationManager.app.storage.repository.postgres.PostgresRepository;
import org.hibernate.Session;
import org.hibernate.cfg.Configuration;

import java.util.ArrayList;
import java.util.List;

public class PostgresUnitOfWork extends UnitOfWork {
    public Session session;
//
    public final IClientRepository clientRepository;
    public final IHallRepository hallRepository;
    public final IPrivilegyRepository privilegyRepository;
    public final ITicketOfficeRepository ticketOfficeRepository;
    public final ITrainStationRepository trainStationRepository;

    public PostgresUnitOfWork() {
        // this.session = PgSessionFactory.openSession();  // TODO: replace with real session factory
        //noinspection resource - session is closed in close() method
        this.session = new Configuration().configure().buildSessionFactory().openSession();

        // replace wiht correct repos
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
        session.getTransaction().commit();
        logger.info("Changes committed.");
    }

    @Override
    public void rollback() {
        session.getTransaction().rollback();
        logger.info("Changes rollback.");
    }

    @Override
    public void close() throws Exception {
        this.rollback();
        this.session.close();
    }
}
