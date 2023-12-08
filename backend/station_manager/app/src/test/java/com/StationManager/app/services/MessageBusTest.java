package com.StationManager.app.services;

import com.StationManager.app.domain.client.Client;
import com.StationManager.app.domain.client.Privilegy;
import com.StationManager.app.domain.commands.AddClientCommand;
import com.StationManager.app.domain.commands.AddTicketOfficeCommand;
import com.StationManager.app.domain.events.ClientAddedEvent;
import com.StationManager.app.domain.events.ClientLeftEvent;
import com.StationManager.app.domain.events.ClientMovedEvent;
import com.StationManager.app.domain.train_station.Direction;
import com.StationManager.app.domain.train_station.Hall;
import com.StationManager.app.domain.train_station.Segment;
import com.StationManager.app.domain.train_station.TicketOffice;
import com.StationManager.app.services.handlers.commands.AddClientCommandHandler;
import com.StationManager.app.services.handlers.commands.AddTicketOfficeCommandHandler;
import com.StationManager.app.services.handlers.events.ClientAddedEventHandler;
import com.StationManager.app.services.handlers.events.ClientLeftEventHandler;
import com.StationManager.app.services.handlers.events.ClientMovedEventHandler;
import com.StationManager.app.services.unitofwork.InMemoryUnitOfWork;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;


public class MessageBusTest {
    private Hall createHall() {
        var ticketOffice = new TicketOffice(1, new Segment(new Point(0, 0), new Point(2, 1)), Direction.Up, 5);
        Hall hall = new Hall(1, new Segment(new Point(0, 0), new Point(10, 10)), List.of(new Segment(new Point(0, 5), new Point(0, 7))), new ArrayList<>());
        hall.addTicketOffice(ticketOffice);
        return hall;
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

    @Test
    void testHandleAddTicketOfficeCommand() {
        var hall = this.createHall();
        var ticketOffice = new TicketOffice(2, new Segment(new Point(8, 0), new Point(9, 2)), Direction.Up, 10);

        var command = new AddTicketOfficeCommand(ticketOffice, hall.getId());
        MessageBus.addCommandHandler(AddTicketOfficeCommand.class, new AddTicketOfficeCommandHandler());

        try (var uow = new InMemoryUnitOfWork()) {
            uow.getHallRepository().add(hall);
            var commands = MessageBus.handle(command, uow);
            List<TicketOffice> ticketOfficeList = uow.getHallRepository().getById(hall.getId()).map(Hall::getTicketOffices).orElse(Collections.emptyList());
            assertEquals(2, ticketOfficeList.size()); //1 command to add office + 1 was added initially
            assertEquals(2, commands.size()); // 2 added offices
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testHandleAddMultipleTicketOfficesCommand() {
        var hall = this.createHall();
        var ticketOffice1 = new TicketOffice(2, new Segment(new Point(3, 0), new Point(4, 1)), Direction.Up, 10);
        var ticketOffice2 = new TicketOffice(3, new Segment(new Point(8, 0), new Point(9, 1)), Direction.Up, 10);
        var ticketOffice3 = new TicketOffice(4, new Segment(new Point(0, 9), new Point(1, 10)), Direction.Left, 10);

        var command1 = new AddTicketOfficeCommand(ticketOffice1, hall.getId());
        var command2 = new AddTicketOfficeCommand(ticketOffice2, hall.getId());
        var command3 = new AddTicketOfficeCommand(ticketOffice3, hall.getId());

        MessageBus.addCommandHandler(AddTicketOfficeCommand.class, new AddTicketOfficeCommandHandler());

        try (var uow = new InMemoryUnitOfWork()) {
            uow.getHallRepository().add(hall);
            MessageBus.handle(command1, uow);
            MessageBus.handle(command2, uow);
            MessageBus.handle(command3, uow);
            List<TicketOffice> ticketOfficeList = uow.getHallRepository().getById(hall.getId()).map(Hall::getTicketOffices).orElse(Collections.emptyList());
            assertEquals(4, ticketOfficeList.size()); //3 commands to add office + 1 was added initially
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testHandleAddClientCommand() {
        var hall = this.createHall();
        var client = new Client(1, "name", "surname", new Privilegy("a", 1), new Point(10, 10));

        var command = new AddClientCommand(client, hall.getId());
        MessageBus.addCommandHandler(AddClientCommand.class, new AddClientCommandHandler());
        MessageBus.addEventHandlers(ClientAddedEvent.class, List.of(new ClientAddedEventHandler()));

        try (var uow = new InMemoryUnitOfWork()) {
            uow.getHallRepository().add(hall);
            MessageBus.handle(command, uow);
            List<TicketOffice> ticketOfficeList = uow.getHallRepository().getById(hall.getId()).map(Hall::getTicketOffices).orElse(Collections.emptyList());
            assertEquals(client, ticketOfficeList.get(0).getQueue().get(0)); //1 command to add office + 1 was added initially
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testHandleAddMultipleClientsCommand() {
        var hall = this.createHall();
        var client1 = new Client(1, "name", "surname", new Privilegy("a", 1), new Point(10, 10));
        var client2 = new Client(1, "name", "surname", new Privilegy("a", 1), new Point(1, 1));

        var command1 = new AddClientCommand(client1, hall.getId());
        var command2 = new AddClientCommand(client2, hall.getId());

        MessageBus.addCommandHandler(AddClientCommand.class, new AddClientCommandHandler());
        MessageBus.addEventHandlers(ClientAddedEvent.class, List.of(new ClientAddedEventHandler()));

        try (var uow = new InMemoryUnitOfWork()) {
            uow.getHallRepository().add(hall);
            MessageBus.handle(command1, uow);
            MessageBus.handle(command2, uow);
            List<TicketOffice> ticketOfficeList = uow.getHallRepository().getById(hall.getId()).map(Hall::getTicketOffices).orElse(Collections.emptyList());
            assertEquals(2, ticketOfficeList.get(0).getQueue().size());
            assertEquals(client1, ticketOfficeList.get(0).getQueue().get(0));
            assertEquals(client2, ticketOfficeList.get(0).getQueue().get(1));

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testHandleClientMovedEvent() {
        var client = new Client(1, "name", "surname", new Privilegy("a", 1), new Point(10, 10));
        var initialPosition = client.getPosition();
        var newPosition = new Point(1, 1);

        MessageBus.addEventHandlers(ClientMovedEvent.class, List.of(new ClientMovedEventHandler()));
        var event = new ClientMovedEvent(client, client.getPosition(), newPosition);

        try (var uow = new InMemoryUnitOfWork()) {
            uow.getClientRepository().add(client);
            MessageBus.handle(event, uow);
            var movedPosition = uow.getClientRepository().getById(1).map(Client::getPosition).orElse(null);
            assertNotEquals(initialPosition, movedPosition);
            assertEquals(newPosition, movedPosition);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testHandleClientLeftEvent() {
        var client = new Client(1, "name", "surname", new Privilegy("a", 1), new Point(10, 10));

        MessageBus.addEventHandlers(ClientLeftEvent.class, List.of(new ClientLeftEventHandler()));
        var event = new ClientLeftEvent(client);

        try (var uow = new InMemoryUnitOfWork()) {
            uow.getClientRepository().add(client);
            MessageBus.handle(event, uow);
            var clientsRepo = uow.getClientRepository();
            assertEquals(0, clientsRepo.getAll().size());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testHandleClientLeftButAnotherIsNotEvent() {
        var hall = this.createHall();
        var client1 = new Client(1, "name", "surname", new Privilegy("a", 1), new Point(10, 10));
        var client2 = new Client(2, "name", "surname", new Privilegy("a", 1), new Point(1, 1));

        MessageBus.addEventHandlers(ClientLeftEvent.class, List.of(new ClientLeftEventHandler()));
        var event = new ClientLeftEvent(client1);

        try (var uow = new InMemoryUnitOfWork()) {
            uow.getClientRepository().add(client1);
            uow.getClientRepository().add(client2);
            MessageBus.handle(event, uow);
            var clientsRepo = uow.getClientRepository();
            assertEquals(1, clientsRepo.getAll().size());
            assertEquals(client2, clientsRepo.getById(2).orElse(null));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
