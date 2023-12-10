package com.StationManager.app;

import com.StationManager.app.domain.client.Client;
//import com.StationManager.app.storage.database.DatabaseConfig;
import com.StationManager.app.domain.client.Privilegy;
import com.StationManager.app.domain.train_station.*;
import com.StationManager.app.services.unitofwork.PostgresUnitOfWork;
import com.StationManager.app.storage.database.dto.ClientDTO;
import com.StationManager.app.storage.database.dto.ClientPositions;
import com.StationManager.app.storage.database.utils.HibernateUtil;
import io.javalin.Javalin;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.locationtech.jts.geom.CoordinateSequence;
import org.locationtech.jts.geom.CoordinateXY;
import org.locationtech.jts.geom.Coordinates;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//import java.awt.*;
import java.awt.*;
import java.util.List;

public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        var session = HibernateUtil.getSessionFactory().openSession();
        var trainStation = new TicketOffice(2, new Segment(new Point(1, 1), new Point(2, 2)), Direction.Up, 1);
//        trainStation.addClient(new Client("a", "b", null, new java.awt.Point(5, 6)));
        trainStation.getQueuePositions().add(new ClientPositions(1, new Client("a", "b", null, new java.awt.Point(5, 6)), trainStation, 0));
//        System.out.println(trainStation.getQueue().get(0).getPosition());
        var tx = session.beginTransaction();
//        session.save(trainStation);

        var a = session.createQuery("select t from TicketOffice t").list();
        System.out.println(((TicketOffice)a.get(0)).getQueuePositions());
//        System.out.println(((TicketOffice) a.get(0)).getId());
//        System.out.println(((TicketOffice) a.get(0)).getQueuePositions().get(1).getId());
//        System.out.println(((TicketOffice) a.get(0)).getQueuePositions().get(1).getClient().getFirstName());
//        System.out.println(((TicketOffice) a.get(0)).getQueuePositions().get(1).getTicketOffice().getSegment());
//        System.out.println(((TicketOffice) a.get(0)).getQueuePositions().get(1).getPositionInQueue());


        tx.commit();
//        var client = new Client("a", "b", null, new java.awt.Point(5, 6));
//        session.save(client);
//        tx.commit();
//
//        var privilegy = new Privilegy("Mother7", 3);
//        tx = session.beginTransaction();
//        session.save(privilegy);
//        tx.commit();


////        var client = new Client("a", "b", null, new java.awt.Point(5, 6));
//        var privilegy = new Privilegy("Mother7", 3);
//        var uow = new PostgresUnitOfWork();
//        uow.getClientRepository().updatePosition(1, new java.awt.Point(40, 36));
//        uow.commit();

//        System.out.println(uow.getClientRepository().getById(1).get().getPrivilegy().getType());
//        uow.getPrivilegyRepository().add(privilegy);
//        uow.commit();
//        uow.rollback();
//        System.out.println(uow.getPrivilegyRepository().getByType("VIP").get().getType());

//        privilegy = new Privilegy("Mother8", 3);
//        System.out.println(uow.session.isOpen());
//        uow.getPrivilegyRepository().add(privilegy);
//        uow.commit();
//        uow.commit();
//        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
//        session.beginTransaction();
//        session.save(client);
//        session.save(privilegy);
//        session.getTransaction().commit();
//        System.out.println("client ID=" + client.getId());

        //terminate session factory, otherwise program won't end
        HibernateUtil.getSessionFactory().close();
    }
}
