package com.StationManager.app.services;

import com.StationManager.app.domain.client.Client;
import com.StationManager.app.domain.client.Privilegy;
import com.StationManager.app.domain.events.ClientAddedEvent;
import com.StationManager.app.domain.events.ClientMovedEvent;
import com.StationManager.app.domain.train_station.Direction;
import com.StationManager.app.domain.train_station.Hall;
import com.StationManager.app.domain.train_station.Segment;
import com.StationManager.app.domain.train_station.TicketOffice;
import com.StationManager.app.services.handlers.events.ClientAddedEventHandler;
import com.StationManager.app.services.unitofwork.InMemoryUnitOfWork;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


public class MessageBusTest {
    private Hall createHall() {
        var ticketOffice = new TicketOffice(
            1,
            new Segment(new Point(0, 0), new Point(2, 1)),
            Direction.Up,
            5
        );
        return new Hall(
            1,
            new Segment(new Point(0, 0), new Point(10, 10)),
            List.of(new Segment(new Point(0, 5), new Point(0, 7))),
            List.of(ticketOffice)
        );
    }
    @Test
    void testNoEventHandlerDoesNothing() {
        var hall = this.createHall();
        var client = new Client(1, "name", "surname", new Privilegy("a", 1), new Point(1, 1));
        var event = new ClientAddedEvent(hall, client);
        try (var uow = new InMemoryUnitOfWork()) {
            MessageBus.handle(event, uow);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testHandleClientAddedEvent() {
        var hall = this.createHall();
        var client = new Client(1, "name", "surname", new Privilegy("a", 1), new Point(10, 10));
        var initialPosition = client.getPosition();

        var event = new ClientAddedEvent(hall, client);
        MessageBus.addEventHandlers(ClientAddedEvent.class, List.of(new ClientAddedEventHandler()));

        try (var uow = new InMemoryUnitOfWork()) {
            uow.getClientRepository().add(client);
            var events = MessageBus.handle(event, uow);
            assertNotEquals(initialPosition, client.getPosition());
            assertEquals(1, events.size());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testHandleClientAddedEventMultipleClients() {
        var hall = this.createHall();
        var client1 = new Client(1, "name", "surname", new Privilegy("a", 1), new Point(10, 10));
        var client2 = new Client(1, "name", "surname", new Privilegy("a", 2), new Point(10, 10));
        var client3 = new Client(1, "name", "surname", new Privilegy("a", 3), new Point(10, 10));
        hall.assignToTicketOffice(client1);
        hall.assignToTicketOffice(client2);

        var event = new ClientAddedEvent(hall, client3);
        MessageBus.addEventHandlers(ClientAddedEvent.class, List.of(new ClientAddedEventHandler()));

        try (var uow = new InMemoryUnitOfWork()) {
            uow.getTicketOfficeRepository().add(hall.getTicketOffices().get(0));
            uow.getClientRepository().add(client1);
            uow.getClientRepository().add(client2);
            uow.getClientRepository().add(client3);

            var events = MessageBus.handle(event, uow);

            // 1 ClientAddedEvent + 4 ClientMovedEvents
            // client1 moved, client2 moved, client2 moved step back, client3 moved
            assertEquals(5, events.size());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
