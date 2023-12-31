package com.StationManager.shared.domain.train_station;

import com.StationManager.shared.domain.client.Client;
import com.StationManager.shared.domain.client.Privilegy;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class HallTest {

    static Client getClient(int id, Point position) {
        return new Client(id, "Fname", "Sname", new Privilegy("ordinary", 0), position);
    }

    static Client getClient(int id, Privilegy privilegy, Point position) {
        return new Client(id, "Fname", "Sname", privilegy, position);
    }
    static Hall getHall(){
        List<Segment> entrances = List.of(
            new Segment(new Point(20, 11), new Point(20, 13))
        );
        ArrayList<TicketOffice> ticketOffices = new ArrayList<>();
        return new Hall(
            1,
            new Segment(new Point(0, 0), new Point(20, 20)),
            entrances,
            ticketOffices
        );
    }

    @Test
    @DisplayName("Adding TicketOffice when Position is Free should update the list")
    void testAddTicketOfficeToHall() {

        Hall hall = getHall();

        Segment ticketOfficeSegment1 = new Segment(new Point(0, 0), new Point(2, 1));
        Segment ticketOfficeSegment2 = new Segment(new Point(0, 19), new Point(1, 20));
        Segment ticketOfficeSegment3 = new Segment(new Point(0, 4), new Point(1, 6));

        int timeToServeTicket = 5;

        TicketOffice ticketOffice1 = new TicketOffice(
            1,
            ticketOfficeSegment1,
            Direction.Up,
            timeToServeTicket
        );
        TicketOffice ticketOffice2 = new TicketOffice(
            2,
            ticketOfficeSegment2,
            Direction.Down,
            timeToServeTicket
        );
        TicketOffice ticketOffice3 = new TicketOffice(
            3,
            ticketOfficeSegment3,
            Direction.Left,
            timeToServeTicket
        );

        hall.addTicketOffice(ticketOffice1);
        hall.addTicketOffice(ticketOffice2);
        hall.addTicketOffice(ticketOffice3);

        assertEquals(3, hall.getTicketOffices().size());
    }

    @Test
    @DisplayName("Adding TicketOffice when Position is taken. Should throw exception")
    void testAddTicketOfficeToHallFailsWhenAddingOnTakenPosition() {

        Hall hall = getHall();

        Segment ticketOfficeSegment1 = new Segment(new Point(0, 19), new Point(2, 20));
        Segment ticketOfficeSegment2 = new Segment(new Point(0, 19), new Point(2, 20));

        int timeToServeTicket = 5;
        TicketOffice ticketOffice1 = new TicketOffice(
            1,
            ticketOfficeSegment1,
            Direction.Down,
            timeToServeTicket
        );
        TicketOffice ticketOffice2 = new TicketOffice(
            2,
            ticketOfficeSegment2,
            Direction.Down,
            timeToServeTicket
        );

        hall.addTicketOffice(ticketOffice1);

        assertThrows(IllegalStateException.class, () -> hall.addTicketOffice(ticketOffice2));
    }

    @ParameterizedTest
    @MethodSource("clientTicketOfficeTestData")
    @DisplayName("Adding client to single ticket office")
    void testAddClientToTicketOffice(Direction direction, Segment ticketOfficeSegment, Point clientPosition, Client expectedClient) {
        Hall hall = getHall();
        TicketOffice ticketOffice = new TicketOffice(1, ticketOfficeSegment, direction, 5);
        hall.addTicketOffice(ticketOffice);

        Client client = getClient(1, clientPosition);
        hall.addClient(client);
        hall.assignToTicketOffice(client);

        assertEquals(expectedClient, ticketOffice.getQueue().get(0));
    }

    private static Stream<Arguments> clientTicketOfficeTestData() {
        return Stream.of(
            Arguments.of(Direction.Up, new Segment(new Point(10, 0), new Point(12, 1)),
                new Point(3, 4), getClient(1, new Point(11, 2))),
            Arguments.of(Direction.Down, new Segment(new Point(10, 19), new Point(12, 20)),
                new Point(3, 4), getClient(1, new Point(11, 18))),
            Arguments.of(Direction.Left, new Segment(new Point(0, 8), new Point(1, 10)),
                new Point(3, 4), getClient(1, new Point(2, 9))),
            Arguments.of(Direction.Right, new Segment(new Point(19, 8), new Point(20, 10)),
                new Point(3, 4), getClient(1, new Point(18, 9)))
        );
    }

    @ParameterizedTest
    @MethodSource("clientsTicketOfficeTestData")
    @DisplayName("Adding clients to single ticket office to check the coordinates of clients in " +
        "the queue")
    void testAddClientsToTicketOffice(Direction direction, Segment ticketOfficeSegment, Point clientPosition, ArrayList<Client> expectedQueue) {
        Hall hall = getHall();
        TicketOffice ticketOffice = new TicketOffice(1, ticketOfficeSegment, direction, 5);
        hall.addTicketOffice(ticketOffice);

        Client client1 = getClient(1, clientPosition);
        Client client2 = getClient(2, new Point(clientPosition.x+1, clientPosition.y));
        hall.addClient(client1);
        hall.assignToTicketOffice(client1);
        hall.addClient(client2);
        hall.assignToTicketOffice(client2);

        assertIterableEquals(expectedQueue, ticketOffice.getQueue());
    }

    private static Stream<Arguments> clientsTicketOfficeTestData() {
        return Stream.of(
            Arguments.of(Direction.Up, new Segment(new Point(10, 0), new Point(12, 1)),
                new Point(3, 4), new ArrayList<>(List.of(
                    getClient(1, new Point(11, 2)),
                    getClient(2, new Point(11, 3))
                ))),
            Arguments.of(Direction.Down, new Segment(new Point(10, 19), new Point(12, 20)),
                new Point(3, 4), new ArrayList<>(List.of(
                    getClient(1, new Point(11, 18)),
                    getClient(2, new Point(11, 17))
                ))),
            Arguments.of(Direction.Left, new Segment(new Point(0, 8), new Point(1, 10)),
                new Point(3, 4), new ArrayList<>(List.of(
                    getClient(1, new Point(2, 9)),
                    getClient(2, new Point(3, 9))
                ))),
            Arguments.of(Direction.Right, new Segment(new Point(19, 8), new Point(20, 10)),
                new Point(3, 4), new ArrayList<>(List.of(
                    getClient(1, new Point(18, 9)),
                    getClient(2, new Point(17, 9))
                )))
        );
    }

    @Test
    @DisplayName(
        "Adding clients to multiple ticket offices. Should be added to the closest with the " +
        "least number of clients"
    )
    void testAddClientToClosestTicketOffice() {

        Hall hall = getHall();

        Segment ticketOfficeSegment1 = new Segment(new Point(9, 0), new Point(11, 1));
        Segment ticketOfficeSegment2 = new Segment(new Point(9, 19), new Point(11, 20));
        Segment ticketOfficeSegment3 = new Segment(new Point(19, 8), new Point(20, 10));

        int timeToServeTicket = 5;

        TicketOffice ticketOffice1 = new TicketOffice(
            1,
            ticketOfficeSegment1,
            Direction.Up,
            timeToServeTicket
        );
        TicketOffice ticketOffice2 = new TicketOffice(
            2,
            ticketOfficeSegment2,
            Direction.Down,
            timeToServeTicket
        );
        TicketOffice ticketOffice3 = new TicketOffice(
            3,
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

        hall.addClient(clientRight1, clientRight1.getPosition());
        hall.assignToTicketOffice(clientRight1);
        assertEquals(ticketOffice3.getQueue().get(0), getClient(1, new Point(18, 9)));

        hall.addClient(clientUp1, clientUp1.getPosition());
        hall.assignToTicketOffice(clientUp1);
        assertEquals(ticketOffice1.getQueue().get(0), getClient(3, new Point(10, 2)));

        hall.addClient(clientDown1, clientDown1.getPosition());
        hall.assignToTicketOffice(clientDown1);
        assertEquals(ticketOffice2.getQueue().get(0), getClient(4, new Point(10, 18)));

        hall.addClient(clientRight2, clientRight2.getPosition());
        hall.assignToTicketOffice(clientRight2);
        assertEquals(ticketOffice3.getQueue().get(1), getClient(2, new Point(17, 9)));

        hall.addClient(clientDown2, clientDown2.getPosition());
        hall.assignToTicketOffice(clientDown2);
        assertEquals(ticketOffice2.getQueue().get(1), getClient(5, new Point(10, 17)));
    }

    @Test
    @DisplayName(
            "Adding client to single ticket office, then removing first client and checking if " +
                "next clients changed their positions correctly")
    void testRemoveClientFromTicketOfficeQueue() {

        Hall hall = getHall();

        Segment ticketOfficeSegment = new Segment(new Point(10, 0), new Point(12, 1));
        TicketOffice ticketOffice = new TicketOffice(1, ticketOfficeSegment, Direction.Up, 5);

        hall.addTicketOffice(ticketOffice);

        Client client1 = getClient(1, new Point(3, 4));
        Client client2 = getClient(2, new Point(4, 4));
        Client client3 = getClient(3, new Point(5, 7));

        hall.addClient(client1);
        hall.assignToTicketOffice(client1);
        hall.addClient(client2);
        hall.assignToTicketOffice(client2);
        hall.addClient(client3);
        hall.assignToTicketOffice(client3);

        hall.getTicketOffices().get(0).removeClient();

        var expectedClientsQueue = new LinkedList<Client>();
        expectedClientsQueue.add(getClient(2, new Point(11, 2)));
        expectedClientsQueue.add(getClient(3, new Point(11, 3)));
        assertIterableEquals(hall.getTicketOffices().get(0).getQueue(), expectedClientsQueue);

        hall.getTicketOffices().get(0).removeClient();
        expectedClientsQueue = new LinkedList<>();
        expectedClientsQueue.add(getClient(3, new Point(11, 2)));
        assertIterableEquals(expectedClientsQueue, hall.getTicketOffices().get(0).getQueue());
    }

    @Test
    @DisplayName("Adding privileged clients with different significance to single ticket office")
    void testAddPrivilegedClientsWithDifferentSignificanceToSingleTicketOffice() {

        Hall hall = getHall();

        Segment ticketOfficeSegment1 = new Segment(new Point(10, 0), new Point(12, 1));
        TicketOffice ticketOffice1 = new TicketOffice(1, ticketOfficeSegment1, Direction.Up, 5);

        hall.addTicketOffice(ticketOffice1);

        Client client1 = getClient(1, new Privilegy("ordinary", 0), new Point(3, 4));
        Client client2 = getClient(2, new Privilegy("ordinary", 0), new Point(3, 5));
        Client client3 = getClient(3, new Privilegy("withChild", 1), new Point(3, 6));
        Client client4 = getClient(4, new Privilegy("disabled", 2), new Point(3, 7));

        hall.addClient(client1);
        hall.assignToTicketOffice(client1);
        hall.addClient(client2);
        hall.assignToTicketOffice(client2);
        hall.addClient(client3);
        hall.assignToTicketOffice(client3);
        hall.addClient(client4);
        hall.assignToTicketOffice(client4);

        var expectedClientsQueue = new LinkedList<Client>();
        expectedClientsQueue.add(getClient(1, new Privilegy("ordinary", 0), new Point(11, 2)));
        expectedClientsQueue.add(getClient(4, new Privilegy("disabled", 2), new Point(11, 3)));
        expectedClientsQueue.add(getClient(3, new Privilegy("withChild", 1), new Point(11, 4)));
        expectedClientsQueue.add(getClient(2, new Privilegy("ordinary", 0), new Point(11, 5)));

        assertIterableEquals(expectedClientsQueue, hall.getTicketOffices().get(0).getQueue());
    }

    @Test
    @DisplayName(
            "Adding privileged clients with the same privilege type and significance to"
                    + " single ticket office")
    void testAddPrivilegedClientsWithSameSignificanceToSingleTicketOffice() {

        Hall hall = getHall();

        Segment ticketOfficeSegment1 = new Segment(new Point(10, 0), new Point(12, 1));
        TicketOffice ticketOffice1 = new TicketOffice(1, ticketOfficeSegment1, Direction.Up, 5);

        hall.addTicketOffice(ticketOffice1);

        Client client1 = getClient(1, new Privilegy("ordinary", 0), new Point(3, 4));
        Client client2 = getClient(2, new Privilegy("ordinary", 0), new Point(3, 5));
        Client client3 = getClient(3, new Privilegy("withChild", 1), new Point(3, 6));
        Client client4 = getClient(4, new Privilegy("disabled", 2), new Point(3, 7));
        Client client5 = getClient(5, new Privilegy("withChild", 1), new Point(3, 8));

        hall.addClient(client1);
        hall.assignToTicketOffice(client1);
        hall.addClient(client2);
        hall.assignToTicketOffice(client2);
        hall.addClient(client3);
        hall.assignToTicketOffice(client3);
        hall.addClient(client4);
        hall.assignToTicketOffice(client4);
        hall.addClient(client5);
        hall.assignToTicketOffice(client5);

        var expectedClientsQueue = new LinkedList<Client>();
        expectedClientsQueue.add(getClient(1, new Privilegy("ordinary", 0), new Point(11, 2)));
        expectedClientsQueue.add(getClient(4, new Privilegy("disabled", 2), new Point(11, 3)));
        expectedClientsQueue.add(getClient(3, new Privilegy("withChild", 1), new Point(11, 4)));
        expectedClientsQueue.add(getClient(5, new Privilegy("withChild", 1), new Point(11, 5)));
        expectedClientsQueue.add(getClient(2, new Privilegy("ordinary", 0), new Point(11, 6)));
        assertIterableEquals(expectedClientsQueue, hall.getTicketOffices().get(0).getQueue());
    }

    @ParameterizedTest
    @MethodSource("ticketOfficeFailSegmentData")
    @DisplayName("Adding TicketOffice not attached to any its side. Should throw exception")
    void testAddTicketOfficeToHallFailsWhenNotAttachedToSide(Direction direction, Segment ticketOfficeSegment ) {
        Hall hall = getHall();
        TicketOffice ticketOffice = new TicketOffice(1, ticketOfficeSegment, direction, 5);

        assertThrows(IllegalStateException.class, () -> hall.addTicketOffice(ticketOffice));
    }

    private static Stream<Arguments> ticketOfficeFailSegmentData() {
        return Stream.of(
            Arguments.of(Direction.Up, new Segment(new Point(12, 3), new Point(14, 4))),
            Arguments.of(Direction.Down, new Segment(new Point(12, 3), new Point(14, 4))),
            Arguments.of(Direction.Left, new Segment(new Point(12, 3), new Point(13, 5))),
            Arguments.of(Direction.Right, new Segment(new Point(12, 3), new Point(13, 5)))
        );
    }

    @Test
    @DisplayName(
        "Creating two ticket offices, placing them the way that collision occurs when both ticket " +
            "offices have 2 clients. On adding new client, this collision should be avoided and" +
            "new client should be added to queue, next free position of which is free")
    void testTicketOfficeQueueWithFreeSpacesFiltration() {
        Hall hall = getHall();

        Segment ticketOfficeSegment1 = new Segment(new Point(1, 0), new Point(3, 1));
        Segment ticketOfficeSegment2 = new Segment(new Point(0, 2), new Point(1, 4));
        TicketOffice ticketOffice1 = new TicketOffice(1, ticketOfficeSegment1, Direction.Up, 5);
        TicketOffice ticketOffice2 = new TicketOffice(1, ticketOfficeSegment2, Direction.Left, 5);

        hall.addTicketOffice(ticketOffice1);
        hall.addTicketOffice(ticketOffice2);

        Client client1 = getClient(1, new Privilegy("ordinary", 0), new Point(3, 4));
        Client client2 = getClient(2, new Privilegy("ordinary", 0), new Point(3, 5));
        Client client3 = getClient(3, new Privilegy("ordinary", 0), new Point(3, 6));
        Client client4 = getClient(4, new Privilegy("ordinary", 0), new Point(3, 7));
        Client client5 = getClient(5, new Privilegy("ordinary", 0), new Point(3, 8));

        ticketOffice1.addClient(client1);
        ticketOffice2.addClient(client2);
        ticketOffice2.addClient(client3);

        hall.addClient(client4);
        hall.assignToTicketOffice(client4);
        hall.addClient(client5);
        hall.assignToTicketOffice(client5);

        var expectedClientsQueue = new LinkedList<Client>();
        expectedClientsQueue.add(getClient(2, new Privilegy("ordinary", 0), new Point(3, 3)));
        expectedClientsQueue.add(getClient(3, new Privilegy("ordinary", 0), new Point(4, 3)));
        expectedClientsQueue.add(getClient(4, new Privilegy("ordinary", 0), new Point(5, 3)));
        expectedClientsQueue.add(getClient(5, new Privilegy("ordinary", 0), new Point(6, 3)));
        assertIterableEquals(expectedClientsQueue, hall.getTicketOffices().get(1).getQueue());
    }
}
