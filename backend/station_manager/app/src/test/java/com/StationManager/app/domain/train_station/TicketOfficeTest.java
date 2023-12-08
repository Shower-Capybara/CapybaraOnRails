package com.StationManager.app.domain.train_station;

import static org.junit.jupiter.api.Assertions.*;

import com.StationManager.app.domain.client.Client;
import com.StationManager.app.domain.client.Privilegy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.LinkedList;
import java.util.List;

class TicketOfficeTest {

    Client getClient(int id, Point position) {
        return new Client(id, "Fname", "Sname", new Privilegy("ordinary", 0), position);
    }

    Client getClient(int id, Privilegy privilegy, Point position) {
        return new Client(id, "Fname", "Sname", privilegy, position);
    }

    @Test
    @DisplayName("Removing client from queue when there are clients")
    void testTicketOfficeRemovesClientFromQueue() {
        Segment ticketOfficeSegment = new Segment(new Point(0, 1), new Point(2, 0));
        TicketOffice ticketOffice = new TicketOffice(1, ticketOfficeSegment, Direction.Down, 5);
        Client client1 = getClient(1, new Point(3, 3));
        Client client2 = getClient(2, new Point(3, 4));

        ticketOffice.addClient(client1);
        ticketOffice.addClient(client2);

        ticketOffice.removeClient();

        LinkedList<Client> expectedResult = new LinkedList<>();
        expectedResult.add(getClient(2, new Point(3, 3)));

        assertIterableEquals(expectedResult, ticketOffice.getQueue());
    }

    @Test
    @DisplayName("Removing client from queue when there are no clients. Should throw exception")
    void testTicketOfficeRemovesClientFromQueueFailsOnEmptyQueue() {
        Segment ticketOfficeSegment = new Segment(new Point(0, 1), new Point(2, 0));
        TicketOffice ticketOffice = new TicketOffice(1, ticketOfficeSegment, Direction.Down, 5);
        assertThrows(IllegalStateException.class, ticketOffice::removeClient);
    }

    @Test
    @DisplayName("Adding client with privilegy.significance == 2 to queue with ordinary clients")
    void testAddClientWithPrivilegyToOrdinaryClientsQueue() {
        Segment ticketOfficeSegment = new Segment(new Point(2, 1), new Point(4, 0));
        TicketOffice ticketOffice = new TicketOffice(1, ticketOfficeSegment, Direction.Down, 5);
        Client client1 = getClient(1, new Privilegy("ordinary", 0), new Point(3, 2));
        Client client2 = getClient(2, new Privilegy("ordinary", 0), new Point(3, 3));

        ticketOffice.addClient(client1);
        ticketOffice.addClient(client2);

        Client client3 = getClient(3, new Privilegy("disabled", 2), new Point(3, 4));
        ticketOffice.addClient(client3);

        var expectedClientsQueue = new LinkedList<>(
            List.of(
                getClient(1, new Privilegy("ordinary", 0), new Point(3, 2)),
                getClient(3, new Privilegy("disabled", 2), new Point(3, 3)),
                getClient(2, new Privilegy("ordinary", 0), new Point(3, 4))
            )
        );
        assertIterableEquals(expectedClientsQueue, ticketOffice.getQueue());
    }

    @Test
    @DisplayName(
        "Adding client with privilegy.significance == 1 to queue with ordinary clients and"
        + " client with privilegy.significance == 2"
    )
    void testAddClientWithLessPrivilegyToQueueWithHigherPrivilegyClient() {
        Segment ticketOfficeSegment = new Segment(new Point(2, 0), new Point(4, 1));
        TicketOffice ticketOffice = new TicketOffice(1, ticketOfficeSegment, Direction.Up, 5);
        Client client1 = getClient(1, new Privilegy("ordinary", 0), new Point(3, 2));
        Client client2 = getClient(2, new Privilegy("ordinary", 0), new Point(3, 3));
        Client client3 = getClient(3, new Privilegy("disabled", 2), new Point(3, 4));

        ticketOffice.addClient(client1);
        ticketOffice.addClient(client2);
        ticketOffice.addClient(client3);

        Client client4 = getClient(4, new Privilegy("withChild", 1), new Point(3, 5));
        ticketOffice.addClient(client4);

        var expectedClientsQueue = new LinkedList<Client>();
        expectedClientsQueue.add(getClient(1, new Privilegy("ordinary", 0), new Point(3, 2)));
        expectedClientsQueue.add(getClient(3, new Privilegy("disabled", 2), new Point(3, 3)));
        expectedClientsQueue.add(getClient(4, new Privilegy("withChild", 1), new Point(3, 4)));
        expectedClientsQueue.add(getClient(2, new Privilegy("ordinary", 0), new Point(3, 5)));

        assertIterableEquals(expectedClientsQueue, ticketOffice.getQueue());
    }
}
