package com.StationManager.app.domain.trainstation;

import static org.junit.jupiter.api.Assertions.*;

import com.StationManager.app.domain.client.Client;
import com.StationManager.app.domain.client.Privilegy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.LinkedList;

class TicketOfficeTest {

    @Test
    @DisplayName("Removing client from queue when there are clients")
    void testTicketOfficeRemovesClientFromQueue() {
        Position ticketOfficePosition = new Position(new Point(0, 1), new Point(2, 0));
        TicketOffice ticketOffice = new TicketOffice(ticketOfficePosition, Direction.Down, 5);
        Client client1 =
                new Client(
                        1,
                        "Fname",
                        "Sname",
                        (short) 2,
                        new Privilegy("ordinary", 0),
                        new Point(3, 3));
        Client client2 =
                new Client(
                        2,
                        "Fname",
                        "Sname",
                        (short) 2,
                        new Privilegy("ordinary", 0),
                        new Point(3, 4));
        ticketOffice.addClient(client1);
        ticketOffice.addClient(client2);

        ticketOffice.removeClient();

        LinkedList<Client> expectedResult = new LinkedList<>();
        expectedResult.add(
                new Client(
                        2,
                        "Fname",
                        "Sname",
                        (short) 2,
                        new Privilegy("ordinary", 0),
                        new Point(3, 3)));

        assertIterableEquals(expectedResult, ticketOffice.getQueue());
    }

    @Test
    @DisplayName("Removing client from queue when there are no clients. Should throw exception")
    void testTicketOfficeRemovesClientFromQueueFailsOnEmptyQueue() {
        Position ticketOfficePosition = new Position(new Point(0, 1), new Point(2, 0));
        TicketOffice ticketOffice = new TicketOffice(ticketOfficePosition, Direction.Down, 5);
        assertThrows(IllegalStateException.class, ticketOffice::removeClient);
    }

    @Test
    @DisplayName("Adding client with privilegy.significance == 2 to queue with ordinary clients")
    void testAddClientWithPrivilegyToOrdinaryClientsQueue() {
        Position ticketOfficePosition = new Position(new Point(2, 1), new Point(4, 0));
        TicketOffice ticketOffice = new TicketOffice(ticketOfficePosition, Direction.Down, 5);
        Client client1 =
                new Client(
                        1,
                        "Fname",
                        "Sname",
                        (short) 2,
                        new Privilegy("ordinary", 0),
                        new Point(3, 2));
        Client client2 =
                new Client(
                        2,
                        "Fname",
                        "Sname",
                        (short) 2,
                        new Privilegy("ordinary", 0),
                        new Point(3, 3));
        ticketOffice.addClient(client1);
        ticketOffice.addClient(client2);

        Client client3 =
                new Client(
                        3,
                        "Fname",
                        "Sname",
                        (short) 2,
                        new Privilegy("disabled", 2),
                        new Point(3, 4));
        ticketOffice.addClient(client3);

        var expectedClientsQueue = new LinkedList<Client>();
        expectedClientsQueue.add(
                new Client(
                        1,
                        "Fname",
                        "Sname",
                        (short) 2,
                        new Privilegy("ordinary", 0),
                        new Point(3, 2)));
        expectedClientsQueue.add(
                new Client(
                        3,
                        "Fname",
                        "Sname",
                        (short) 2,
                        new Privilegy("disabled", 2),
                        new Point(3, 3)));
        expectedClientsQueue.add(
                new Client(
                        2,
                        "Fname",
                        "Sname",
                        (short) 2,
                        new Privilegy("ordinary", 0),
                        new Point(3, 4)));

        assertIterableEquals(expectedClientsQueue, ticketOffice.getQueue());
    }

    @Test
    @DisplayName(
            "Adding client with privilegy.significance == 1 to queue with ordinary clients and"
                    + " client with privilegy.significance == 2")
    void testAddClientWithLessPrivilegyToQueueWithHigherPrivilegyClient() {
        Position ticketOfficePosition = new Position(new Point(2, 1), new Point(4, 0));
        TicketOffice ticketOffice = new TicketOffice(ticketOfficePosition, Direction.Down, 5);
        Client client1 =
                new Client(
                        1,
                        "Fname",
                        "Sname",
                        (short) 2,
                        new Privilegy("ordinary", 0),
                        new Point(3, 2));
        Client client2 =
                new Client(
                        2,
                        "Fname",
                        "Sname",
                        (short) 2,
                        new Privilegy("ordinary", 0),
                        new Point(3, 3));

        Client client3 =
                new Client(
                        3,
                        "Fname",
                        "Sname",
                        (short) 2,
                        new Privilegy("disabled", 2),
                        new Point(3, 4));

        ticketOffice.addClient(client1);
        ticketOffice.addClient(client2);
        ticketOffice.addClient(client3);

        Client client4 =
                new Client(
                        4,
                        "Fname",
                        "Sname",
                        (short) 2,
                        new Privilegy("withChild", 1),
                        new Point(3, 5));
        ticketOffice.addClient(client4);

        var expectedClientsQueue = new LinkedList<Client>();
        expectedClientsQueue.add(
                new Client(
                        1,
                        "Fname",
                        "Sname",
                        (short) 2,
                        new Privilegy("ordinary", 0),
                        new Point(3, 2)));
        expectedClientsQueue.add(
                new Client(
                        3,
                        "Fname",
                        "Sname",
                        (short) 2,
                        new Privilegy("disabled", 2),
                        new Point(3, 3)));
        expectedClientsQueue.add(
                new Client(
                        4,
                        "Fname",
                        "Sname",
                        (short) 2,
                        new Privilegy("withChild", 1),
                        new Point(3, 4)));
        expectedClientsQueue.add(
                new Client(
                        2,
                        "Fname",
                        "Sname",
                        (short) 2,
                        new Privilegy("ordinary", 0),
                        new Point(3, 5)));

        assertIterableEquals(expectedClientsQueue, ticketOffice.getQueue());
    }
}
