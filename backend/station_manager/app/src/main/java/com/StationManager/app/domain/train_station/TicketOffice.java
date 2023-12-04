package com.StationManager.app.domain.train_station;

import com.StationManager.app.domain.ServeRecord;
import com.StationManager.app.domain.client.Client;
import com.StationManager.app.domain.events.ClientLeftEvent;
import com.StationManager.app.domain.events.ClientMovedEvent;
import com.StationManager.app.domain.events.Event;

import java.awt.Point;
import java.util.*;

public class TicketOffice {
    private final Integer id;
    private Segment segment;
    private final List<Client> queue;
    private Integer timeToServeTicket;
    private Boolean isClosed;
    private Boolean isReserved;
    private Direction direction;
    private Iterable<ServeRecord> transactions;

    public final Queue<Event> events = new LinkedList<>();

    public TicketOffice(
        Integer id,
        Segment segment,
        Direction direction,
        Integer timeToServeTicket
    ) {
        this.id = id;
        this.segment = segment;
        this.queue = new LinkedList<>();
        this.timeToServeTicket = timeToServeTicket;
        this.isClosed = false;
        this.isReserved = false;
        this.direction = direction;
        this.transactions = new ArrayList<>();
    }

    public void removeClient() {
        if (queue.isEmpty()) {
            throw new IllegalStateException(
                "Client queue is empty: There are no clients to delete"
            );
        }
        var newEvents = new ArrayList<Event>();
        var removedClient = queue.remove(0);
        newEvents.add(new ClientLeftEvent(removedClient));

        Point previousClientPosition = removedClient.getPosition();
        for (Client client : queue) {
            Point currentClientPosition = client.getPosition();
            client.setPosition(previousClientPosition);
            newEvents.add(new ClientMovedEvent(client, previousClientPosition));
            previousClientPosition = currentClientPosition;
        }

        this.events.addAll(newEvents);
    }

    public void addClient(Client client) {
        if (queue.isEmpty() || client.getPrivilegy() == null) {
            queue.add(client);
            return;
        }
        var newEvents = new ArrayList<Event>();
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
            newEvents.add(new ClientMovedEvent(currentClient, currentClient.getPosition()));
        }
        // Change the position for the last client
        nextClient.setPosition(insertedClientPosition);
        newEvents.add(new ClientMovedEvent(nextClient, insertedClientPosition));
        Collections.reverse(newEvents);
        this.events.addAll(newEvents);
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

    public Integer getId() { return this.id;}
    public List<Client> getQueue() { return this.queue; }
    public Direction getDirection() { return this.direction; }
    public Segment getSegment() { return this.segment; }
    public Boolean getClosed() { return this.isClosed; }
    public Boolean getReserved() { return isReserved; }
    public Integer getTimeToServeTicket() { return timeToServeTicket; }
    public Iterable<ServeRecord> getTransactions() { return transactions; }

    public void setDirection(Direction direction) { this.direction = direction; }
    public void setSegment(Segment segment) { this.segment = segment; }
    public void setClosed(Boolean bool) { this.isClosed = bool; }
    public void setReserved(Boolean reserved) { isReserved = reserved; }
    public void setTimeToServeTicket(Integer timeToServeTicket) { this.timeToServeTicket = timeToServeTicket; }
    public void setTransactions(Iterable<ServeRecord> transactions) { this.transactions = transactions; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TicketOffice that)) return false;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
