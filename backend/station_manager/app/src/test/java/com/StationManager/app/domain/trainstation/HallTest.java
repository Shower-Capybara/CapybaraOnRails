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

        Position ticketOfficePosition1 = new Position(new Point(0, 1), new Point(2, 0));
        Position ticketOfficePosition2 = new Position(new Point(0, 7), new Point(1, 5));
        Position ticketOfficePosition3 = new Position(new Point(10, 6), new Point(11, 4));

        TicketOffice ticketOffice1 = new TicketOffice(ticketOfficePosition1, Direction.Down);
        TicketOffice ticketOffice2 = new TicketOffice(ticketOfficePosition2, Direction.Left);
        TicketOffice ticketOffice3 = new TicketOffice(ticketOfficePosition3, Direction.Right);

        Hall hall =
                new Hall(
                        new Position(new Point(0, 20), new Point(20, 0)), entrances, ticketOffices);

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
                        new Position(new Point(0, 20), new Point(20, 0)), entrances, ticketOffices);

        Position ticketOfficePosition1 = new Position(new Point(0, 1), new Point(2, 0));
        Position ticketOfficePosition2 = new Position(new Point(0, 1), new Point(2, 0));

        TicketOffice ticketOffice1 = new TicketOffice(ticketOfficePosition1, Direction.Down);
        TicketOffice ticketOffice2 = new TicketOffice(ticketOfficePosition2, Direction.Down);

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
                        new Position(new Point(0, 20), new Point(20, 0)), entrances, ticketOffices);

        Position ticketOfficePosition1 = new Position(new Point(10, 10), new Point(12, 9));
        TicketOffice ticketOffice1 = new TicketOffice(ticketOfficePosition1, Direction.Up);

        hall.addTicketOffice(ticketOffice1);
        ticketOffice1.setClosed(false);

        Client client1 =
                new Client(2, "Fname", "Sname", (short) 2, new Privilegy("12", 1), new Point(3, 4));

        hall.addClient(client1);

        assertEquals(
                ticketOffice1.getQueue().getClients().get(0),
                new Client(
                        2, "Fname", "Sname", (short) 2, new Privilegy("12", 1), new Point(11, 8)));
    }

    @Test
    @DisplayName("Adding client to single ticket office in down")
    void testAddClientToSingleDownTicketOffice() {
        ArrayList<Position> entrances = new ArrayList<>();
        ArrayList<TicketOffice> ticketOffices = new ArrayList<>();

        Hall hall =
                new Hall(
                        new Position(new Point(0, 20), new Point(20, 0)), entrances, ticketOffices);

        Position ticketOfficePosition1 = new Position(new Point(10, 10), new Point(12, 9));
        TicketOffice ticketOffice1 = new TicketOffice(ticketOfficePosition1, Direction.Down);

        hall.addTicketOffice(ticketOffice1);
        ticketOffice1.setClosed(false);

        Client client1 =
                new Client(2, "Fname", "Sname", (short) 2, new Privilegy("12", 1), new Point(3, 4));

        hall.addClient(client1);

        assertEquals(
                ticketOffice1.getQueue().getClients().get(0),
                new Client(
                        2, "Fname", "Sname", (short) 2, new Privilegy("12", 1), new Point(11, 11)));
    }

    @Test
    @DisplayName("Adding client to single ticket office in left")
    void testAddClientToSingleLeftTicketOffice() {
        ArrayList<Position> entrances = new ArrayList<>();
        ArrayList<TicketOffice> ticketOffices = new ArrayList<>();

        Hall hall =
                new Hall(
                        new Position(new Point(0, 20), new Point(20, 0)), entrances, ticketOffices);

        Position ticketOfficePosition1 = new Position(new Point(10, 10), new Point(11, 8));
        TicketOffice ticketOffice1 = new TicketOffice(ticketOfficePosition1, Direction.Left);

        hall.addTicketOffice(ticketOffice1);
        ticketOffice1.setClosed(false);

        Client client1 =
                new Client(2, "Fname", "Sname", (short) 2, new Privilegy("12", 1), new Point(3, 4));

        hall.addClient(client1);

        assertEquals(
                ticketOffice1.getQueue().getClients().get(0),
                new Client(
                        2, "Fname", "Sname", (short) 2, new Privilegy("12", 1), new Point(12, 9)));
    }

    @Test
    @DisplayName("Adding client to single ticket office in right")
    void testAddClientToSingleRightTicketOffice() {
        ArrayList<Position> entrances = new ArrayList<>();
        ArrayList<TicketOffice> ticketOffices = new ArrayList<>();

        Hall hall =
                new Hall(
                        new Position(new Point(0, 20), new Point(20, 0)), entrances, ticketOffices);

        Position ticketOfficePosition1 = new Position(new Point(10, 10), new Point(11, 8));
        TicketOffice ticketOffice1 = new TicketOffice(ticketOfficePosition1, Direction.Right);

        hall.addTicketOffice(ticketOffice1);
        ticketOffice1.setClosed(false);

        Client client1 =
                new Client(2, "Fname", "Sname", (short) 2, new Privilegy("12", 1), new Point(3, 4));

        hall.addClient(client1);

        assertEquals(
                ticketOffice1.getQueue().getClients().get(0),
                new Client(
                        2, "Fname", "Sname", (short) 2, new Privilegy("12", 1), new Point(9, 9)));
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
                        new Position(new Point(0, 20), new Point(20, 0)), entrances, ticketOffices);

        Position ticketOfficePosition1 = new Position(new Point(9, 1), new Point(11, 0));
        Position ticketOfficePosition2 = new Position(new Point(9, 20), new Point(11, 19));
        Position ticketOfficePosition3 = new Position(new Point(19, 10), new Point(20, 8));

        TicketOffice ticketOffice1 = new TicketOffice(ticketOfficePosition1, Direction.Down);
        TicketOffice ticketOffice2 = new TicketOffice(ticketOfficePosition2, Direction.Up);
        TicketOffice ticketOffice3 = new TicketOffice(ticketOfficePosition3, Direction.Right);

        ticketOffice1.setClosed(false);
        ticketOffice2.setClosed(false);
        ticketOffice3.setClosed(false);

        hall.addTicketOffice(ticketOffice1);
        hall.addTicketOffice(ticketOffice2);
        hall.addTicketOffice(ticketOffice3);

        Client clientRight1 =
                new Client(
                        1, "Fname", "Sname", (short) 2, new Privilegy("12", 1), new Point(18, 7));
        Client clientRight2 =
                new Client(
                        2, "Fname", "Sname", (short) 2, new Privilegy("12", 1), new Point(19, 12));
        Client clientUp1 =
                new Client(
                        3, "Fname", "Sname", (short) 2, new Privilegy("12", 1), new Point(9, 17));
        Client clientDown1 =
                new Client(4, "Fname", "Sname", (short) 2, new Privilegy("12", 1), new Point(7, 1));
        Client clientDown2 =
                new Client(5, "Fname", "Sname", (short) 2, new Privilegy("12", 1), new Point(0, 0));

        hall.addClient(clientRight1);
        assertEquals(
                ticketOffice3.getQueue().getClients().get(0),
                new Client(
                        1, "Fname", "Sname", (short) 2, new Privilegy("12", 1), new Point(18, 9)));

        hall.addClient(clientUp1);
        assertEquals(
                ticketOffice2.getQueue().getClients().get(0),
                new Client(
                        3, "Fname", "Sname", (short) 2, new Privilegy("12", 1), new Point(10, 18)));

        hall.addClient(clientDown1);
        assertEquals(
                ticketOffice1.getQueue().getClients().get(0),
                new Client(
                        4, "Fname", "Sname", (short) 2, new Privilegy("12", 1), new Point(10, 2)));

        hall.addClient(clientRight2);
        assertEquals(
                ticketOffice3.getQueue().getClients().get(1),
                new Client(
                        2, "Fname", "Sname", (short) 2, new Privilegy("12", 1), new Point(17, 9)));

        hall.addClient(clientDown2);
        assertEquals(
                ticketOffice1.getQueue().getClients().get(1),
                new Client(
                        5, "Fname", "Sname", (short) 2, new Privilegy("12", 1), new Point(10, 3)));
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
                        new Position(new Point(0, 20), new Point(20, 0)), entrances, ticketOffices);

        Position ticketOfficePosition = new Position(new Point(10, 10), new Point(12, 9));
        TicketOffice ticketOffice = new TicketOffice(ticketOfficePosition, Direction.Up);

        hall.addTicketOffice(ticketOffice);
        ticketOffice.setClosed(false);

        Client client1 =
                new Client(1, "Fname", "Sname", (short) 2, new Privilegy("12", 1), new Point(3, 4));
        Client client2 =
                new Client(2, "Fname", "Sname", (short) 2, new Privilegy("12", 1), new Point(4, 4));
        Client client3 =
                new Client(3, "Fname", "Sname", (short) 2, new Privilegy("12", 1), new Point(5, 7));

        hall.addClient(client1);
        hall.addClient(client2);
        hall.addClient(client3);

        hall.getTicketOffices().get(0).removeClient();

        LinkedList<Client> expectedClientsQueue = new LinkedList<Client>();
        expectedClientsQueue.add(
                new Client(
                        2, "Fname", "Sname", (short) 2, new Privilegy("12", 1), new Point(11, 8)));
        expectedClientsQueue.add(
                new Client(
                        3, "Fname", "Sname", (short) 2, new Privilegy("12", 1), new Point(11, 7)));
        assertIterableEquals(
                hall.getTicketOffices().get(0).getQueue().getClients(), expectedClientsQueue);

        hall.getTicketOffices().get(0).removeClient();
        expectedClientsQueue = new LinkedList<Client>();
        expectedClientsQueue.add(
                new Client(
                        3, "Fname", "Sname", (short) 2, new Privilegy("12", 1), new Point(11, 8)));
        assertIterableEquals(
                hall.getTicketOffices().get(0).getQueue().getClients(), expectedClientsQueue);
    }
}
