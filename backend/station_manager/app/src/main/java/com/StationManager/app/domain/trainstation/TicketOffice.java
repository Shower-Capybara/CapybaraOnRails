package com.StationManager.app.domain.trainstation;

import com.StationManager.app.domain.Transaction;
import com.StationManager.app.domain.client.Client;

import java.awt.Point;
import java.util.ArrayList;
import java.util.LinkedList;

public class TicketOffice {
    private Position position;
    private LinkedList<Client> queue;
    private Integer timeToServeTicket;
    private Boolean isClosed;
    private Boolean isReserved;
    private Direction direction;
    private Iterable<Transaction> transactions;

    // public Iterable<IEvent> events;

    public TicketOffice(Position position, Direction direction, Integer timeToServeTicket) {
        this.position = position;
        this.queue = new LinkedList<>();
        this.timeToServeTicket = timeToServeTicket;
        this.isClosed = false;
        this.isReserved = false;
        this.direction = direction;
        this.transactions = new ArrayList<>();
    }

    public LinkedList<Client> getQueue() {
        return this.queue;
    }

    public Direction getDirection() {
        return this.direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public Position getPosition() {
        return this.position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public void setClosed(Boolean bool) {
        this.isClosed = bool;
    }

    public Boolean getClosed() {
        return this.isClosed;
    }

    public void serveClient() {
        throw new UnsupportedOperationException("This method is not yet implemented");
    }

    // public Iterable<IEvent> RemoveClient(){
    public void removeClient() {
        if (queue.isEmpty()) {
            throw new IllegalStateException(
                    "Client queue is empty: There are no clients to delete");
        }
        var removedClient = queue.pop();
        Point previousClientPosition = removedClient.getPosition();
        for (Client client : queue) {
            Point currentClientPosition = client.getPosition();
            client.setPosition(previousClientPosition);
            previousClientPosition = currentClientPosition;
        }
    }

    public void addClient(Client client) {
        if (queue.isEmpty() || client.getPrivilegy() == null) {
            queue.add(client);
            return;
        }
        // Find the position to insert the new client
        int insertIndex = findInsertIndex(client);
        queue.add(insertIndex, client);

        // Take into account the change in customer positions during queue changes
        Point insertedClientPosition = new Point(client.getPosition());
        for (int i = insertIndex; i < queue.size() - 1; i++) {
            var currentClient = queue.get(i);
            var nextClient = queue.get(i + 1);
            currentClient.setPosition(nextClient.getPosition());
        }
        // Change the position for the last client
        queue.getLast().setPosition(insertedClientPosition);
    }

    private int findInsertIndex(Client newClient) {
        // Check if there are only ordinary clients in the queue
        boolean hasOnlyOrdinaryClients =
                queue.stream().allMatch(c -> c.getPrivilegy().getType().equals("ordinary"));

        if (hasOnlyOrdinaryClients && (!newClient.getPrivilegy().getType().equals("ordinary"))) {
            // Insert new client at position 1
            return 1;
        } else {
            // Iterate through the queue to find the appropriate position based on privilege type
            // and priority
            for (int i = 1; i < queue.size(); i++) {
                Client existingClient = queue.get(i);

                // Compare privilege types
                int significanceComparison =
                        newClient
                                .getPrivilegy()
                                .getSignificance()
                                .compareTo(existingClient.getPrivilegy().getSignificance());

                if (significanceComparison > 0) {
                    // The newClient has a higher privilege type, insert before existingClient
                    return i;
                }
            }
        }

        // If no specific position is found, insert at the end
        return queue.size();
    }
}
