package com.StationManager.app.domain.trainstation;

import static org.junit.jupiter.api.Assertions.*;

import com.StationManager.app.domain.Queue;
import com.StationManager.app.domain.client.Client;
import com.StationManager.app.domain.client.Privilegy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.awt.*;

class TicketOfficeTest {

    @Test
    @DisplayName("Removing client from queue when there are clients")
    void testTicketOfficeRemovesClientFromQueue() {
        Position ticketOfficePosition = new Position(new Point(0, 1), new Point(2, 0));
        TicketOffice ticketOffice = new TicketOffice(ticketOfficePosition, Direction.Down);
        Client client1 =
                new Client(1, "Fname", "Sname", (short) 2, new Privilegy("12", 1), new Point(3, 3));
        Client client2 =
                new Client(2, "Fname", "Sname", (short) 2, new Privilegy("12", 1), new Point(3, 4));
        ticketOffice.addClient(client1);
        ticketOffice.addClient(client2);

        ticketOffice.removeClient();

        Queue expectedResult = new Queue();
        expectedResult.add(
                new Client(
                        2, "Fname", "Sname", (short) 2, new Privilegy("12", 1), new Point(3, 3)));

        assertIterableEquals(expectedResult.getClients(), ticketOffice.getQueue().getClients());
    }

    @Test
    @DisplayName("Removing client from queue when there are no clients. Should throw exception")
    void testTicketOfficeRemovesClientFromQueueFailsOnEmptyQueue() {
        Position ticketOfficePosition = new Position(new Point(0, 1), new Point(2, 0));
        TicketOffice ticketOffice = new TicketOffice(ticketOfficePosition, Direction.Down);
        assertThrows(IllegalStateException.class, ticketOffice::removeClient);
    }
}
