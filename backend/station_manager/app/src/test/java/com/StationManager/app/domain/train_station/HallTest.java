package com.StationManager.app.domain.train_station;

import static org.junit.jupiter.api.Assertions.*;

import com.StationManager.app.domain.client.Client;
import com.StationManager.app.domain.client.Privilegy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;

class HallTest {

    Client getClient(int id, Point position) {
        return new Client(id, "Fname", "Sname", new Privilegy("ordinary", 0), position);
    }

    Client getClient(int id, Privilegy privilegy, Point position) {
        return new Client(id, "Fname", "Sname", privilegy, position);
    }

    @Test
    @DisplayName("Adding TicketOffice when Position is Free should update the list")
    void testAddTicketOfficeToHall() {
        ArrayList<Segment> entrances = new ArrayList<>();
        ArrayList<TicketOffice> ticketOffices = new ArrayList<>();

        Segment ticketOfficeSegment1 = new Segment(new Point(0, 0), new Point(2, 1));
        Segment ticketOfficeSegment2 = new Segment(new Point(0, 5), new Point(1, 7));
        Segment ticketOfficeSegment3 = new Segment(new Point(10, 4), new Point(11, 6));

        int timeToServeTicket = 5;

        TicketOffice ticketOffice1 = new TicketOffice(
            ticketOfficeSegment1,
            Direction.Down,
            timeToServeTicket
        );
        TicketOffice ticketOffice2 = new TicketOffice(
            ticketOfficeSegment2,
            Direction.Left,
            timeToServeTicket
        );
        TicketOffice ticketOffice3 = new TicketOffice(
            ticketOfficeSegment3,
            Direction.Right,
            timeToServeTicket
        );

        Hall hall = new Hall(
            new Segment(new Point(0, 0), new Point(20, 20)),
            entrances,
            ticketOffices
        );

        hall.addTicketOffice(ticketOffice1);
        hall.addTicketOffice(ticketOffice2);
        hall.addTicketOffice(ticketOffice3);

        assertEquals(3, hall.getTicketOffices().size());
    }

    @Test
    @DisplayName("Adding TicketOffice when Position is taken. Should throw exception")
    void testAddTicketOfficeToHallFailsWhenAddingOnTakenPosition() {
        ArrayList<Segment> entrances = new ArrayList<>();
        ArrayList<TicketOffice> ticketOffices = new ArrayList<>();

        Hall hall = new Hall(
            new Segment(new Point(0, 0), new Point(20, 20)),
            entrances,
            ticketOffices
        );

        Segment ticketOfficeSegment1 = new Segment(new Point(0, 0), new Point(2, 1));
        Segment ticketOfficeSegment2 = new Segment(new Point(0, 0), new Point(2, 1));

        int timeToServeTicket = 5;
        TicketOffice ticketOffice1 = new TicketOffice(
            ticketOfficeSegment1,
            Direction.Down,
            timeToServeTicket
        );
        TicketOffice ticketOffice2 = new TicketOffice(
            ticketOfficeSegment2,
            Direction.Down,
            timeToServeTicket
        );

        hall.addTicketOffice(ticketOffice1);

        assertThrows(IllegalStateException.class, () -> hall.addTicketOffice(ticketOffice2));
    }

    @Test
    @DisplayName("Adding client to single ticket office in up")
    void testAddClientToSingleUpTicketOffice() {
        ArrayList<Segment> entrances = new ArrayList<>();
        ArrayList<TicketOffice> ticketOffices = new ArrayList<>();

        Hall hall = new Hall(
            new Segment(new Point(0, 0), new Point(20, 20)),
            entrances,
            ticketOffices
        );

        Segment ticketOfficeSegment1 = new Segment(new Point(10, 9), new Point(12, 10));
        TicketOffice ticketOffice1 = new TicketOffice(ticketOfficeSegment1, Direction.Up, 5);
        hall.addTicketOffice(ticketOffice1);
        hall.addClient(getClient(1, new Point(3, 4)));

        assertEquals(ticketOffice1.getQueue().get(0), getClient(1, new Point(11, 11)));
    }

    @Test
    @DisplayName("Adding client to single ticket office in down")
        void testAddClientToSingleDownTicketOffice() {
        ArrayList<Segment> entrances = new ArrayList<>();
        ArrayList<TicketOffice> ticketOffices = new ArrayList<>();

        Hall hall = new Hall(
            new Segment(new Point(0, 0), new Point(20, 20)),
            entrances,
            ticketOffices
        );

        Segment ticketOfficeSegment1 = new Segment(new Point(10, 9), new Point(12, 10));
        TicketOffice ticketOffice1 = new TicketOffice(ticketOfficeSegment1, Direction.Down, 5);

        hall.addTicketOffice(ticketOffice1);

        Client client1 = getClient(1, new Point(3, 4));

        hall.addClient(client1);

        assertEquals(ticketOffice1.getQueue().get(0), getClient(1, new Point(11, 8)));
    }

    @Test
    @DisplayName("Adding client to single ticket office in left")
    void testAddClientToSingleLeftTicketOffice() {
        ArrayList<Segment> entrances = new ArrayList<>();
        ArrayList<TicketOffice> ticketOffices = new ArrayList<>();

        Hall hall = new Hall(
            new Segment(new Point(0, 0), new Point(20, 20)),
            entrances,
            ticketOffices
        );

        Segment ticketOfficeSegment1 = new Segment(new Point(10, 8), new Point(11, 10));
        TicketOffice ticketOffice1 = new TicketOffice(ticketOfficeSegment1, Direction.Left, 5);

        hall.addTicketOffice(ticketOffice1);

        Client client1 = getClient(1, new Point(3, 4));

        hall.addClient(client1);

        assertEquals(ticketOffice1.getQueue().get(0), getClient(1, new Point(12, 9)));
    }

    @Test
    @DisplayName("Adding client to single ticket office in right")
    void testAddClientToSingleRightTicketOffice() {
        ArrayList<Segment> entrances = new ArrayList<>();
        ArrayList<TicketOffice> ticketOffices = new ArrayList<>();

        Hall hall = new Hall(
            new Segment(new Point(0, 0), new Point(20, 20)),
            entrances,
            ticketOffices
        );

        Segment ticketOfficeSegment1 = new Segment(new Point(10, 8), new Point(11, 10));
        TicketOffice ticketOffice1 = new TicketOffice(ticketOfficeSegment1, Direction.Right, 5);

        hall.addTicketOffice(ticketOffice1);

        Client client1 = getClient(1, new Point(3, 4));

        hall.addClient(client1);

        assertEquals(ticketOffice1.getQueue().get(0), getClient(1, new Point(9, 9)));
    }

    @Test
    @DisplayName(
        "Adding clients to multiple ticket offices. Should be added to the closest with the " +
        "least number of clients"
    )
    void testAddClientsToMultipleTicketsOffices() {
        ArrayList<Segment> entrances = new ArrayList<>();
        ArrayList<TicketOffice> ticketOffices = new ArrayList<>();

        Hall hall = new Hall(new Segment(new Point(0, 0), new Point(20, 20)), entrances, ticketOffices);

        Segment ticketOfficeSegment1 = new Segment(new Point(9, 0), new Point(11, 1));
        Segment ticketOfficeSegment2 = new Segment(new Point(9, 19), new Point(11, 20));
        Segment ticketOfficeSegment3 = new Segment(new Point(19, 8), new Point(20, 10));

        int timeToServeTicket = 5;

        TicketOffice ticketOffice1 = new TicketOffice(
            ticketOfficeSegment1,
            Direction.Up,
            timeToServeTicket
        );
        TicketOffice ticketOffice2 = new TicketOffice(
            ticketOfficeSegment2,
            Direction.Down,
            timeToServeTicket
        );
        TicketOffice ticketOffice3 = new TicketOffice(
            ticketOfficeSegment3,
            Direction.Right,
            timeToServeTicket
        );

        hall.addTicketOffice(ticketOffice1);
        hall.addTicketOffice(ticketOffice2);
        hall.addTicketOffice(ticketOffice3);

        Client clientRight1 = getClient(1, new Point(18, 13));

        Client clientRight2 = getClient(2, new Point(19, 6));
        Client clientUp1 = getClient(3, new Point(9, 3));
        Client clientDown1 = getClient(4, new Point(7, 19));
        Client clientDown2 = getClient(5, new Point(20, 20));

        hall.addClient(clientRight1);
        assertEquals(ticketOffice3.getQueue().get(0), getClient(1, new Point(18, 9)));

        hall.addClient(clientUp1);
        assertEquals(ticketOffice1.getQueue().get(0), getClient(3, new Point(10, 2)));

        hall.addClient(clientDown1);
        assertEquals(ticketOffice2.getQueue().get(0), getClient(4, new Point(10, 18)));

        hall.addClient(clientRight2);
        assertEquals(ticketOffice3.getQueue().get(1), getClient(2, new Point(17, 9)));

        hall.addClient(clientDown2);
        assertEquals(ticketOffice2.getQueue().get(1), getClient(5, new Point(10, 17)));
    }

    @Test
    @DisplayName(
            "Adding clients to multiple ticket offices. Should be added to the closest with the"
                    + " least number of clients")
    void testRemoveClientFromTicketOfficeQueue() {
        ArrayList<Segment> entrances = new ArrayList<>();
        ArrayList<TicketOffice> ticketOffices = new ArrayList<>();

        Hall hall = new Hall(
            new Segment(new Point(0, 0), new Point(20, 20)),
            entrances,
            ticketOffices
        );

        Segment ticketOfficeSegment = new Segment(new Point(10, 9), new Point(12, 10));
        TicketOffice ticketOffice = new TicketOffice(ticketOfficeSegment, Direction.Up, 5);

        hall.addTicketOffice(ticketOffice);

        Client client1 = getClient(1, new Point(3, 4));
        Client client2 = getClient(2, new Point(4, 4));
        Client client3 = getClient(3, new Point(5, 7));

        hall.addClient(client1);
        hall.addClient(client2);
        hall.addClient(client3);

        hall.getTicketOffices().get(0).removeClient();

        var expectedClientsQueue = new LinkedList<Client>();
        expectedClientsQueue.add(getClient(2, new Point(11, 11)));
        expectedClientsQueue.add(getClient(3, new Point(11, 12)));
        assertIterableEquals(hall.getTicketOffices().get(0).getQueue(), expectedClientsQueue);

        hall.getTicketOffices().get(0).removeClient();
        expectedClientsQueue = new LinkedList<>();
        expectedClientsQueue.add(getClient(3, new Point(11, 11)));
        assertIterableEquals(expectedClientsQueue, hall.getTicketOffices().get(0).getQueue());
    }

    @Test
    @DisplayName("Adding privileged client to single ticket office in up")
    void testAddPrivilegedClientToSingleUpTicketOffice() {
        ArrayList<Segment> entrances = new ArrayList<>();
        ArrayList<TicketOffice> ticketOffices = new ArrayList<>();

        Hall hall = new Hall(new Segment(new Point(0, 0), new Point(20, 20)), entrances, ticketOffices);

        Segment ticketOfficeSegment1 = new Segment(new Point(10, 9), new Point(12, 10));
        TicketOffice ticketOffice1 = new TicketOffice(ticketOfficeSegment1, Direction.Up, 5);

        hall.addTicketOffice(ticketOffice1);
        Client client1 = getClient(1, new Privilegy("ordinary", 0), new Point(3, 4));
        Client client2 = getClient(2, new Privilegy("ordinary", 0), new Point(3, 5));
        Client client3 = getClient(3, new Privilegy("withChild", 1), new Point(3, 6));

        hall.addClient(client1);
        hall.addClient(client2);
        hall.addClient(client3);

        var expectedClientsQueue = new LinkedList<Client>();
        expectedClientsQueue.add(getClient(1, new Privilegy("ordinary", 0), new Point(11, 11)));
        expectedClientsQueue.add(getClient(3, new Privilegy("withChild", 1), new Point(11, 12)));
        expectedClientsQueue.add(getClient(2, new Privilegy("ordinary", 0), new Point(11, 13)));
        assertIterableEquals(expectedClientsQueue, hall.getTicketOffices().get(0).getQueue());
    }

    @Test
    @DisplayName("Adding privileged clients to single ticket office in up")
    void testAddPrivilegedClientsToSingleUpTicketOffice() {
        ArrayList<Segment> entrances = new ArrayList<>();
        ArrayList<TicketOffice> ticketOffices = new ArrayList<>();

        Hall hall = new Hall(new Segment(new Point(0, 0), new Point(20, 20)), entrances, ticketOffices);

        Segment ticketOfficeSegment1 = new Segment(new Point(10, 9), new Point(12, 10));
        TicketOffice ticketOffice1 = new TicketOffice(ticketOfficeSegment1, Direction.Up, 5);

        hall.addTicketOffice(ticketOffice1);

        Client client1 = getClient(1, new Privilegy("ordinary", 0), new Point(3, 4));
        Client client2 = getClient(2, new Privilegy("ordinary", 0), new Point(3, 5));
        Client client3 = getClient(3, new Privilegy("withChild", 1), new Point(3, 6));
        Client client4 = getClient(4, new Privilegy("disabled", 2), new Point(3, 7));

        hall.addClient(client1);
        hall.addClient(client2);
        hall.addClient(client3);
        hall.addClient(client4);

        var expectedClientsQueue = new LinkedList<Client>();
        expectedClientsQueue.add(getClient(1, new Privilegy("ordinary", 0), new Point(11, 11)));
        expectedClientsQueue.add(getClient(4, new Privilegy("disabled", 2), new Point(11, 12)));
        expectedClientsQueue.add(getClient(3, new Privilegy("withChild", 1), new Point(11, 13)));
        expectedClientsQueue.add(getClient(2, new Privilegy("ordinary", 0), new Point(11, 14)));

        assertIterableEquals(expectedClientsQueue, hall.getTicketOffices().get(0).getQueue());
    }

    @Test
    @DisplayName(
            "Adding privileged clients with the same privilege type and significance to"
                    + " single ticket office in up")
    void testAddPrivilegedClientsWithDifferentSignificanceToSingleUpTicketOffice() {
        ArrayList<Segment> entrances = new ArrayList<>();
        ArrayList<TicketOffice> ticketOffices = new ArrayList<>();

        Hall hall = new Hall(new Segment(new Point(0, 0), new Point(20, 20)), entrances, ticketOffices);

        Segment ticketOfficeSegment1 = new Segment(new Point(10, 9), new Point(12, 10));
        TicketOffice ticketOffice1 = new TicketOffice(ticketOfficeSegment1, Direction.Up, 5);

        hall.addTicketOffice(ticketOffice1);

        Client client1 = getClient(1, new Privilegy("ordinary", 0), new Point(3, 4));
        Client client2 = getClient(2, new Privilegy("ordinary", 0), new Point(3, 5));
        Client client3 = getClient(3, new Privilegy("withChild", 1), new Point(3, 6));
        Client client4 = getClient(4, new Privilegy("disabled", 2), new Point(3, 7));
        Client client5 = getClient(5, new Privilegy("withChild", 1), new Point(3, 8));

        hall.addClient(client1);
        hall.addClient(client2);
        hall.addClient(client3);
        hall.addClient(client4);
        hall.addClient(client5);

        var expectedClientsQueue = new LinkedList<Client>();
        expectedClientsQueue.add(getClient(1, new Privilegy("ordinary", 0), new Point(11, 11)));
        expectedClientsQueue.add(getClient(4, new Privilegy("disabled", 2), new Point(11, 12)));
        expectedClientsQueue.add(getClient(3, new Privilegy("withChild", 1), new Point(11, 13)));
        expectedClientsQueue.add(getClient(5, new Privilegy("withChild", 1), new Point(11, 14)));
        expectedClientsQueue.add(getClient(2, new Privilegy("ordinary", 0), new Point(11, 15)));
        assertIterableEquals(expectedClientsQueue, hall.getTicketOffices().get(0).getQueue());
    }
}
