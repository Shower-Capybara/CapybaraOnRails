package com.StationManager.app.domain.trainstation;

import static org.junit.jupiter.api.Assertions.*;

import com.StationManager.app.domain.client.Client;
import com.StationManager.app.domain.client.Privilegy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;

class HallTest {

    @Test
    @DisplayName("Adding TicketOffice when Position is Free should update the list")
    void testAddTicketOfficeToHall() {
        ArrayList<Position> entrances = new ArrayList<>();
        ArrayList<TicketOffice> ticketOffices = new ArrayList<>();

        Position ticketOfficePosition1 = new Position(new Point(0, 0), new Point(2, 1));
        Position ticketOfficePosition2 = new Position(new Point(0, 5), new Point(1, 7));
        Position ticketOfficePosition3 = new Position(new Point(10, 4), new Point(11, 6));

        int timeToServeTicket = 5;

        TicketOffice ticketOffice1 =
                new TicketOffice(ticketOfficePosition1, Direction.Down, timeToServeTicket);
        TicketOffice ticketOffice2 =
                new TicketOffice(ticketOfficePosition2, Direction.Left, timeToServeTicket);
        TicketOffice ticketOffice3 =
                new TicketOffice(ticketOfficePosition3, Direction.Right, timeToServeTicket);

        Hall hall =
                new Hall(
                        new Position(new Point(0, 0), new Point(20, 20)), entrances, ticketOffices);

        hall.addTicketOffice(ticketOffice1);
        hall.addTicketOffice(ticketOffice2);
        hall.addTicketOffice(ticketOffice3);

        assertEquals(3, hall.getTicketOffices().size());
    }

    @Test
    @DisplayName("Adding TicketOffice when Position is taken. Should throw exception")
    void testAddTicketOfficeToHallFailsWhenAddingOnTakenPosition() {
        ArrayList<Position> entrances = new ArrayList<>();
        ArrayList<TicketOffice> ticketOffices = new ArrayList<>();

        Hall hall =
                new Hall(
                        new Position(new Point(0, 0), new Point(20, 20)), entrances, ticketOffices);

        Position ticketOfficePosition1 = new Position(new Point(0, 0), new Point(2, 1));
        Position ticketOfficePosition2 = new Position(new Point(0, 0), new Point(2, 1));

        int timeToServeTicket = 5;
        TicketOffice ticketOffice1 =
                new TicketOffice(ticketOfficePosition1, Direction.Down, timeToServeTicket);
        TicketOffice ticketOffice2 =
                new TicketOffice(ticketOfficePosition2, Direction.Down, timeToServeTicket);

        hall.addTicketOffice(ticketOffice1);

        assertThrows(IllegalStateException.class, () -> hall.addTicketOffice(ticketOffice2));
    }

    @Test
    @DisplayName("Adding client to single ticket office in up")
    void testAddClientToSingleUpTicketOffice() {
        ArrayList<Position> entrances = new ArrayList<>();
        ArrayList<TicketOffice> ticketOffices = new ArrayList<>();

        Hall hall =
                new Hall(
                        new Position(new Point(0, 0), new Point(20, 20)), entrances, ticketOffices);

        Position ticketOfficePosition1 = new Position(new Point(10, 9), new Point(12, 10));
        TicketOffice ticketOffice1 = new TicketOffice(ticketOfficePosition1, Direction.Up, 5);

        hall.addTicketOffice(ticketOffice1);

        Client client1 =
                new Client(
                        2,
                        "Fname",
                        "Sname",
                        (short) 2,
                        new Privilegy("ordinary", 0),
                        new Point(3, 4));

        hall.addClient(client1);

        assertEquals(
                ticketOffice1.getQueue().getClients().get(0),
                new Client(
                        2,
                        "Fname",
                        "Sname",
                        (short) 2,
                        new Privilegy("ordinary", 0),
                        new Point(11, 11)));
    }

    @Test
    @DisplayName("Adding client to single ticket office in down")
    void testAddClientToSingleDownTicketOffice() {
        ArrayList<Position> entrances = new ArrayList<>();
        ArrayList<TicketOffice> ticketOffices = new ArrayList<>();

        Hall hall =
                new Hall(
                        new Position(new Point(0, 0), new Point(20, 20)), entrances, ticketOffices);

        Position ticketOfficePosition1 = new Position(new Point(10, 9), new Point(12, 10));
        TicketOffice ticketOffice1 = new TicketOffice(ticketOfficePosition1, Direction.Down, 5);

        hall.addTicketOffice(ticketOffice1);

        Client client1 =
                new Client(
                        2,
                        "Fname",
                        "Sname",
                        (short) 2,
                        new Privilegy("ordinary", 0),
                        new Point(3, 4));

        hall.addClient(client1);

        assertEquals(
                ticketOffice1.getQueue().getClients().get(0),
                new Client(
                        2,
                        "Fname",
                        "Sname",
                        (short) 2,
                        new Privilegy("ordinary", 0),
                        new Point(11, 8)));
    }

    @Test
    @DisplayName("Adding client to single ticket office in left")
    void testAddClientToSingleLeftTicketOffice() {
        ArrayList<Position> entrances = new ArrayList<>();
        ArrayList<TicketOffice> ticketOffices = new ArrayList<>();

        Hall hall =
                new Hall(
                        new Position(new Point(0, 0), new Point(20, 20)), entrances, ticketOffices);

        Position ticketOfficePosition1 = new Position(new Point(10, 8), new Point(11, 10));
        TicketOffice ticketOffice1 = new TicketOffice(ticketOfficePosition1, Direction.Left, 5);

        hall.addTicketOffice(ticketOffice1);

        Client client1 =
                new Client(
                        2,
                        "Fname",
                        "Sname",
                        (short) 2,
                        new Privilegy("ordinary", 0),
                        new Point(3, 4));

        hall.addClient(client1);

        assertEquals(
                ticketOffice1.getQueue().getClients().get(0),
                new Client(
                        2,
                        "Fname",
                        "Sname",
                        (short) 2,
                        new Privilegy("ordinary", 0),
                        new Point(12, 9)));
    }

    @Test
    @DisplayName("Adding client to single ticket office in right")
    void testAddClientToSingleRightTicketOffice() {
        ArrayList<Position> entrances = new ArrayList<>();
        ArrayList<TicketOffice> ticketOffices = new ArrayList<>();

        Hall hall =
                new Hall(
                        new Position(new Point(0, 0), new Point(20, 20)), entrances, ticketOffices);

        Position ticketOfficePosition1 = new Position(new Point(10, 8), new Point(11, 10));
        TicketOffice ticketOffice1 = new TicketOffice(ticketOfficePosition1, Direction.Right, 5);

        hall.addTicketOffice(ticketOffice1);

        Client client1 =
                new Client(
                        2,
                        "Fname",
                        "Sname",
                        (short) 2,
                        new Privilegy("ordinary", 0),
                        new Point(3, 4));

        hall.addClient(client1);

        assertEquals(
                ticketOffice1.getQueue().getClients().get(0),
                new Client(
                        2,
                        "Fname",
                        "Sname",
                        (short) 2,
                        new Privilegy("ordinary", 0),
                        new Point(9, 9)));
    }

    @Test
    @DisplayName(
            "Adding clients to multiple ticket offices. Should be added to the closest with the"
                    + " least number of clients")
    void testAddClientsToMultipleTicketsOffices() {
        ArrayList<Position> entrances = new ArrayList<>();
        ArrayList<TicketOffice> ticketOffices = new ArrayList<>();

        Hall hall =
                new Hall(
                        new Position(new Point(0, 0), new Point(20, 20)), entrances, ticketOffices);

        Position ticketOfficePosition1 = new Position(new Point(9, 0), new Point(11, 1));
        Position ticketOfficePosition2 = new Position(new Point(9, 19), new Point(11, 20));
        Position ticketOfficePosition3 = new Position(new Point(19, 8), new Point(20, 10));

        int timeToServeTicket = 5;

        TicketOffice ticketOffice1 =
                new TicketOffice(ticketOfficePosition1, Direction.Up, timeToServeTicket);
        TicketOffice ticketOffice2 =
                new TicketOffice(ticketOfficePosition2, Direction.Down, timeToServeTicket);
        TicketOffice ticketOffice3 =
                new TicketOffice(ticketOfficePosition3, Direction.Right, timeToServeTicket);

        hall.addTicketOffice(ticketOffice1);
        hall.addTicketOffice(ticketOffice2);
        hall.addTicketOffice(ticketOffice3);

        Client clientRight1 =
                new Client(
                        1,
                        "Fname",
                        "Sname",
                        (short) 2,
                        new Privilegy("ordinary", 0),
                        new Point(18, 13));
        Client clientRight2 =
                new Client(
                        2,
                        "Fname",
                        "Sname",
                        (short) 2,
                        new Privilegy("ordinary", 0),
                        new Point(19, 6));
        Client clientUp1 =
                new Client(
                        3,
                        "Fname",
                        "Sname",
                        (short) 2,
                        new Privilegy("ordinary", 0),
                        new Point(9, 3));
        Client clientDown1 =
                new Client(
                        4,
                        "Fname",
                        "Sname",
                        (short) 2,
                        new Privilegy("ordinary", 0),
                        new Point(7, 19));
        Client clientDown2 =
                new Client(
                        5,
                        "Fname",
                        "Sname",
                        (short) 2,
                        new Privilegy("ordinary", 0),
                        new Point(20, 20));

        hall.addClient(clientRight1);
        assertEquals(
                ticketOffice3.getQueue().getClients().get(0),
                new Client(
                        1,
                        "Fname",
                        "Sname",
                        (short) 2,
                        new Privilegy("ordinary", 0),
                        new Point(18, 9)));

        hall.addClient(clientUp1);
        assertEquals(
                ticketOffice1.getQueue().getClients().get(0),
                new Client(
                        3,
                        "Fname",
                        "Sname",
                        (short) 2,
                        new Privilegy("ordinary", 0),
                        new Point(10, 2)));

        hall.addClient(clientDown1);
        assertEquals(
                ticketOffice2.getQueue().getClients().get(0),
                new Client(
                        4,
                        "Fname",
                        "Sname",
                        (short) 2,
                        new Privilegy("ordinary", 0),
                        new Point(10, 18)));

        hall.addClient(clientRight2);
        assertEquals(
                ticketOffice3.getQueue().getClients().get(1),
                new Client(
                        2,
                        "Fname",
                        "Sname",
                        (short) 2,
                        new Privilegy("ordinary", 0),
                        new Point(17, 9)));

        hall.addClient(clientDown2);
        assertEquals(
                ticketOffice2.getQueue().getClients().get(1),
                new Client(
                        5,
                        "Fname",
                        "Sname",
                        (short) 2,
                        new Privilegy("ordinary", 0),
                        new Point(10, 17)));
    }

    @Test
    @DisplayName(
            "Adding clients to multiple ticket offices. Should be added to the closest with the"
                    + " least number of clients")
    void testRemoveClientFromTicketOfficeQueue() {
        ArrayList<Position> entrances = new ArrayList<>();
        ArrayList<TicketOffice> ticketOffices = new ArrayList<>();

        Hall hall =
                new Hall(
                        new Position(new Point(0, 0), new Point(20, 20)), entrances, ticketOffices);

        Position ticketOfficePosition = new Position(new Point(10, 9), new Point(12, 10));
        TicketOffice ticketOffice = new TicketOffice(ticketOfficePosition, Direction.Up, 5);

        hall.addTicketOffice(ticketOffice);

        Client client1 =
                new Client(
                        1,
                        "Fname",
                        "Sname",
                        (short) 2,
                        new Privilegy("ordinary", 0),
                        new Point(3, 4));
        Client client2 =
                new Client(
                        2,
                        "Fname",
                        "Sname",
                        (short) 2,
                        new Privilegy("ordinary", 0),
                        new Point(4, 4));
        Client client3 =
                new Client(
                        3,
                        "Fname",
                        "Sname",
                        (short) 2,
                        new Privilegy("ordinary", 0),
                        new Point(5, 7));

        hall.addClient(client1);
        hall.addClient(client2);
        hall.addClient(client3);

        hall.getTicketOffices().get(0).removeClient();

        LinkedList<Client> expectedClientsQueue = new LinkedList<Client>();
        expectedClientsQueue.add(
                new Client(
                        2,
                        "Fname",
                        "Sname",
                        (short) 2,
                        new Privilegy("ordinary", 0),
                        new Point(11, 11)));
        expectedClientsQueue.add(
                new Client(
                        3,
                        "Fname",
                        "Sname",
                        (short) 2,
                        new Privilegy("ordinary", 0),
                        new Point(11, 12)));
        assertIterableEquals(
                hall.getTicketOffices().get(0).getQueue().getClients(), expectedClientsQueue);

        hall.getTicketOffices().get(0).removeClient();
        expectedClientsQueue = new LinkedList<Client>();
        expectedClientsQueue.add(
                new Client(
                        3,
                        "Fname",
                        "Sname",
                        (short) 2,
                        new Privilegy("ordinary", 0),
                        new Point(11, 11)));
        assertIterableEquals(
                expectedClientsQueue, hall.getTicketOffices().get(0).getQueue().getClients());
    }

    @Test
    @DisplayName("Adding privileged client to single ticket office in up")
    void testAddPrivilegedClientToSingleUpTicketOffice() {
        ArrayList<Position> entrances = new ArrayList<>();
        ArrayList<TicketOffice> ticketOffices = new ArrayList<>();

        Hall hall =
                new Hall(
                        new Position(new Point(0, 0), new Point(20, 20)), entrances, ticketOffices);

        Position ticketOfficePosition1 = new Position(new Point(10, 9), new Point(12, 10));
        TicketOffice ticketOffice1 = new TicketOffice(ticketOfficePosition1, Direction.Up, 5);

        hall.addTicketOffice(ticketOffice1);

        Client client1 =
                new Client(
                        1,
                        "Fname",
                        "Sname",
                        (short) 2,
                        new Privilegy("ordinary", 0),
                        new Point(3, 4));
        Client client2 =
                new Client(
                        2,
                        "Fname",
                        "Sname",
                        (short) 2,
                        new Privilegy("ordinary", 0),
                        new Point(3, 5));
        Client client3 =
                new Client(
                        3,
                        "Fname",
                        "Sname",
                        (short) 2,
                        new Privilegy("withChild", 1),
                        new Point(3, 6));

        hall.addClient(client1);
        hall.addClient(client2);
        hall.addClient(client3);

        var expectedClientsQueue = new LinkedList<Client>();
        expectedClientsQueue.add(
                new Client(
                        1,
                        "Fname",
                        "Sname",
                        (short) 2,
                        new Privilegy("ordinary", 0),
                        new Point(11, 11)));
        expectedClientsQueue.add(
                new Client(
                        3,
                        "Fname",
                        "Sname",
                        (short) 2,
                        new Privilegy("withChild", 1),
                        new Point(11, 12)));
        expectedClientsQueue.add(
                new Client(
                        2,
                        "Fname",
                        "Sname",
                        (short) 2,
                        new Privilegy("ordinary", 0),
                        new Point(11, 13)));

        assertIterableEquals(
                expectedClientsQueue, hall.getTicketOffices().get(0).getQueue().getClients());
    }

    @Test
    @DisplayName("Adding privileged clients to single ticket office in up")
    void testAddPrivilegedClientsToSingleUpTicketOffice() {
        ArrayList<Position> entrances = new ArrayList<>();
        ArrayList<TicketOffice> ticketOffices = new ArrayList<>();

        Hall hall =
                new Hall(
                        new Position(new Point(0, 0), new Point(20, 20)), entrances, ticketOffices);

        Position ticketOfficePosition1 = new Position(new Point(10, 9), new Point(12, 10));
        TicketOffice ticketOffice1 = new TicketOffice(ticketOfficePosition1, Direction.Up, 5);

        hall.addTicketOffice(ticketOffice1);

        Client client1 =
                new Client(
                        1,
                        "Fname",
                        "Sname",
                        (short) 2,
                        new Privilegy("ordinary", 0),
                        new Point(3, 4));
        Client client2 =
                new Client(
                        2,
                        "Fname",
                        "Sname",
                        (short) 2,
                        new Privilegy("ordinary", 0),
                        new Point(3, 5));
        Client client3 =
                new Client(
                        3,
                        "Fname",
                        "Sname",
                        (short) 2,
                        new Privilegy("withChild", 1),
                        new Point(3, 6));
        Client client4 =
                new Client(
                        4,
                        "Fname",
                        "Sname",
                        (short) 2,
                        new Privilegy("disabled", 2),
                        new Point(3, 7));

        hall.addClient(client1);
        hall.addClient(client2);
        hall.addClient(client3);
        hall.addClient(client4);

        var expectedClientsQueue = new LinkedList<Client>();
        expectedClientsQueue.add(
                new Client(
                        1,
                        "Fname",
                        "Sname",
                        (short) 2,
                        new Privilegy("ordinary", 0),
                        new Point(11, 11)));
        expectedClientsQueue.add(
                new Client(
                        4,
                        "Fname",
                        "Sname",
                        (short) 2,
                        new Privilegy("disabled", 2),
                        new Point(11, 12)));
        expectedClientsQueue.add(
                new Client(
                        3,
                        "Fname",
                        "Sname",
                        (short) 2,
                        new Privilegy("withChild", 1),
                        new Point(11, 13)));
        expectedClientsQueue.add(
                new Client(
                        2,
                        "Fname",
                        "Sname",
                        (short) 2,
                        new Privilegy("ordinary", 0),
                        new Point(11, 14)));
        assertIterableEquals(
                expectedClientsQueue, hall.getTicketOffices().get(0).getQueue().getClients());
    }

    @Test
    @DisplayName(
            "Adding privileged clients with the same privilege type but different significance to"
                    + " single ticket office in up")
    void testAddPrivilegedClientsWithDifferentSignificanceToSingleUpTicketOffice() {
        ArrayList<Position> entrances = new ArrayList<>();
        ArrayList<TicketOffice> ticketOffices = new ArrayList<>();

        Hall hall =
                new Hall(
                        new Position(new Point(0, 0), new Point(20, 20)), entrances, ticketOffices);

        Position ticketOfficePosition1 = new Position(new Point(10, 9), new Point(12, 10));
        TicketOffice ticketOffice1 = new TicketOffice(ticketOfficePosition1, Direction.Up, 5);

        hall.addTicketOffice(ticketOffice1);

        Client client1 =
                new Client(
                        1,
                        "Fname",
                        "Sname",
                        (short) 2,
                        new Privilegy("ordinary", 0),
                        new Point(3, 4));
        Client client2 =
                new Client(
                        2,
                        "Fname",
                        "Sname",
                        (short) 2,
                        new Privilegy("ordinary", 0),
                        new Point(3, 5));
        Client client3 =
                new Client(
                        3,
                        "Fname",
                        "Sname",
                        (short) 2,
                        new Privilegy("withChild", 1),
                        new Point(3, 6));
        Client client4 =
                new Client(
                        4,
                        "Fname",
                        "Sname",
                        (short) 2,
                        new Privilegy("disabled", 2),
                        new Point(3, 7));
        Client client5 =
                new Client(
                        5,
                        "Fname",
                        "Sname",
                        (short) 2,
                        new Privilegy("withChild", 1),
                        new Point(3, 8));

        hall.addClient(client1);
        hall.addClient(client2);
        hall.addClient(client3);
        hall.addClient(client4);
        hall.addClient(client5);

        var expectedClientsQueue = new LinkedList<Client>();
        expectedClientsQueue.add(
                new Client(
                        1,
                        "Fname",
                        "Sname",
                        (short) 2,
                        new Privilegy("ordinary", 0),
                        new Point(11, 11)));
        expectedClientsQueue.add(
                new Client(
                        4,
                        "Fname",
                        "Sname",
                        (short) 2,
                        new Privilegy("disabled", 2),
                        new Point(11, 12)));
        expectedClientsQueue.add(
                new Client(
                        3,
                        "Fname",
                        "Sname",
                        (short) 2,
                        new Privilegy("withChild", 1),
                        new Point(11, 13)));
        expectedClientsQueue.add(
                new Client(
                        5,
                        "Fname",
                        "Sname",
                        (short) 2,
                        new Privilegy("withChild", 1),
                        new Point(11, 14)));
        expectedClientsQueue.add(
                new Client(
                        2,
                        "Fname",
                        "Sname",
                        (short) 2,
                        new Privilegy("ordinary", 0),
                        new Point(11, 15)));
        assertIterableEquals(
                expectedClientsQueue, hall.getTicketOffices().get(0).getQueue().getClients());
    }
}
