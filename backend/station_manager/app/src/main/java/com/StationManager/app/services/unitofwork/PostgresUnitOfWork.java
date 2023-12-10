package com.StationManager.app.services.unitofwork;

import com.StationManager.app.storage.database.utils.HibernateUtil;
import com.StationManager.app.storage.repository.postgres.PostgresClientRepository;
import com.StationManager.app.storage.repository.postgres.PostgresPrivilegyRepository;
import org.hibernate.Session;

public class PostgresUnitOfWork extends UnitOfWork {
    public Session session;

    public PostgresUnitOfWork() {
        this.session = HibernateUtil.getSessionFactory().openSession();
//
//        this.clientRepository = new InMemoryClientRepository();
//        this.hallRepository = new InMemoryHallRepository();
//        this.privilegyRepository = new InMemoryPrivilegyRepository();
//        this.ticketOfficeRepository = new InMemoryTicketOfficeRepository();
//        this.trainStationRepository = new InMemoryTrainStationRepository();
        this.privilegyRepository = new PostgresPrivilegyRepository(session);
        this.clientRepository = new PostgresClientRepository(session);
    }

    @Override
    public void commit() {
        this.session.getTransaction().commit();
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

    @Override
    public PostgresPrivilegyRepository getPrivilegyRepository() {
        if (!this.session.getTransaction().isActive()) this.session.beginTransaction();
        return (PostgresPrivilegyRepository) this.privilegyRepository;
    }

    @Override
    public PostgresClientRepository getClientRepository() {
        if (!this.session.getTransaction().isActive()) this.session.beginTransaction();
        return (PostgresClientRepository) this.clientRepository;
    }
}
