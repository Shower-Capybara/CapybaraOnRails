package com.StationManager.app.storage.unitofwork;

import com.StationManager.app.domain.client.*;
import com.StationManager.app.domain.train_station.Hall;
import com.StationManager.app.domain.train_station.TicketOffice;
import com.StationManager.app.domain.train_station.TrainStation;
import com.StationManager.app.storage.repository.*;
import com.StationManager.app.storage.repository.postgres.PostgresRepository;
import org.hibernate.Session;
import org.hibernate.cfg.Configuration;

public class PostgresUnitOfWork extends UnitOfWork {
    public Session session;
    public IRepository<Client> clientRepository;
    public IRepository<Hall> hallRepository;
    public IRepository<Privilegy> privilegyRepository;
    public IRepository<TicketOffice> ticketOfficeRepository;
    public IRepository<TrainStation> trainStationRepository;

    public PostgresUnitOfWork() {
        // this.session = PgSessionFactory.openSession();  // TODO: replace with real session factory
        //noinspection resource - session is closed in close() method
        this.session = new Configuration().configure().buildSessionFactory().openSession();

        clientRepository = new PostgresRepository<>(session);
        hallRepository = new PostgresRepository<>(session);
        privilegyRepository = new PostgresRepository<>(session);
        ticketOfficeRepository = new PostgresRepository<>(session);
        trainStationRepository = new PostgresRepository<>(session);
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
