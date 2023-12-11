package com.StationManager.shared.services.unitofwork;

import com.StationManager.shared.storage.database.utils.HibernateUtil;
import com.StationManager.shared.storage.repository.postgres.*;
import org.hibernate.Session;

public class PostgresUnitOfWork extends UnitOfWork {
    public final Session session;

    public PostgresUnitOfWork() {
        this.session = HibernateUtil.getSessionFactory().openSession();

        this.privilegyRepository = new PostgresPrivilegyRepository(session);
        this.clientRepository = new PostgresClientRepository(session);
        this.ticketOfficeRepository = new PostgresTicketOfficeRepository(session);
        this.hallRepository = new PostgresHallRepository(session);
        this.trainStationRepository = new PostgresTrainStationRepository(session);
    }

    @Override
    public void commit() {
        this.session.getTransaction().commit();
        this.session.clear();  // clear cache, so that we can get fresh data from database

        // clearing seen sets
        this.privilegyRepository.getSeen().clear();
        this.clientRepository.getSeen().clear();
        this.ticketOfficeRepository.getSeen().clear();
        this.hallRepository.getSeen().clear();
        this.trainStationRepository.getSeen().clear();

        logger.info("Changes committed.");
    }

    @Override
    public void rollback() {
        session.getTransaction().rollback();
    }

    @Override
    public void close() {
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

    @Override
    public PostgresTicketOfficeRepository getTicketOfficeRepository() {
        if (!this.session.getTransaction().isActive()) this.session.beginTransaction();
        return (PostgresTicketOfficeRepository) this.ticketOfficeRepository;
    }

    @Override
    public PostgresHallRepository getHallRepository() {
        if (!this.session.getTransaction().isActive()) this.session.beginTransaction();
        return (PostgresHallRepository) this.hallRepository;
    }

    @Override
    public PostgresTrainStationRepository getTrainStationRepository() {
        if (!this.session.getTransaction().isActive()) this.session.beginTransaction();
        return (PostgresTrainStationRepository) this.trainStationRepository;
    }
}
