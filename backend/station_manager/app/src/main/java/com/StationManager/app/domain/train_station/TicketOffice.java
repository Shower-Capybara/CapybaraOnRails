package com.StationManager.app.domain.train_station;

import com.StationManager.app.domain.ServeRecord;
import com.StationManager.app.domain.client.Client;

import java.awt.Point;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Objects;

public class TicketOffice {
    private Integer id;
    private Segment segment;
    private final LinkedList<Client> queue;
    private Integer timeToServeTicket;
    private Boolean isClosed;
    private Boolean isReserved;
    private Direction direction;
    private Iterable<ServeRecord> transactions;

    // public Iterable<IEvent> events;

    public TicketOffice(Segment segment, Direction direction, Integer timeToServeTicket) {
        this.segment = segment;
        this.queue = new LinkedList<>();
        this.timeToServeTicket = timeToServeTicket;
        this.isClosed = false;
        this.isReserved = false;
        this.direction = direction;
        this.transactions = new ArrayList<>();
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Segment getSegment() {
        return this.segment;
    }

    public void setPosition(Segment segment) {
        this.segment = segment;
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
                "Client queue is empty: There are no clients to delete"
            );
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

        Client nextClient = queue.get(insertIndex);
        // Take into account the change in customer positions during queue changes
        Point insertedClientPosition = new Point(client.getPosition());
        for (int i = insertIndex; i < queue.size() - 1; i++) {
            var currentClient = queue.get(i);
            nextClient = queue.get(i + 1);
            currentClient.setPosition(nextClient.getPosition());
        }
        // Change the position for the last client
        nextClient.setPosition(insertedClientPosition);
    }

    private int findInsertIndex(Client newClient) {
        int index = 0;
        for (var client: queue) {
            if (
                newClient.getPrivilegy().getSignificance() > client.getPrivilegy().getSignificance()
                && index != 0
            ) break;

            index += 1;
        }
        return index;
    }

    public Integer getTimeToServeTicket() {
        return timeToServeTicket;
    }

    public void setTimeToServeTicket(Integer timeToServeTicket) {
        this.timeToServeTicket = timeToServeTicket;
    }

    public Boolean getReserved() {
        return isReserved;
    }

    public void setReserved(Boolean reserved) {
        isReserved = reserved;
    }

    public Iterable<ServeRecord> getTransactions() {
        return transactions;
    }

    public void setTransactions(Iterable<ServeRecord> transactions) {
        this.transactions = transactions;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TicketOffice that)) return false;
        return Objects.equals(segment, that.segment)
            && Objects.equals(queue, that.queue)
            && Objects.equals(timeToServeTicket, that.timeToServeTicket)
            && Objects.equals(isClosed, that.isClosed)
            && Objects.equals(isReserved, that.isReserved)
            && direction == that.direction
            && Objects.equals(transactions, that.transactions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(segment, queue, timeToServeTicket, isClosed, isReserved, direction, transactions);
    }
}
