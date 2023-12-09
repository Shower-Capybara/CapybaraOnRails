package com.StationManager.app.services.unitofwork;

import com.StationManager.shared.domain.events.Event;
import com.StationManager.app.storage.repository.*;
import com.StationManager.app.storage.repository.inmemory.*;
import org.hibernate.Session;
import org.hibernate.cfg.Configuration;

import java.util.ArrayList;
import java.util.List;

public class PostgresUnitOfWork extends UnitOfWork {
    public Session session;

    public PostgresUnitOfWork() {
        // this.session = PgSessionFactory.openSession();  // TODO: replace with real session factory
        //noinspection resource - session is closed in close() method
        this.session = new Configuration().configure().buildSessionFactory().openSession();

        // replace with correct repos
        this.clientRepository = new InMemoryClientRepository();
        this.hallRepository = new InMemoryHallRepository();
        this.privilegyRepository = new InMemoryPrivilegyRepository();
        this.ticketOfficeRepository = new InMemoryTicketOfficeRepository();
        this.trainStationRepository = new InMemoryTrainStationRepository();
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
