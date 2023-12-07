package com.StationManager.app.domain.train_station;

import com.StationManager.app.domain.MapManager;
import com.StationManager.app.domain.ServeRecord;
import com.StationManager.app.domain.client.Client;
import com.StationManager.app.domain.events.ClientLeftEvent;
import com.StationManager.app.domain.events.ClientMovedEvent;
import com.StationManager.app.domain.events.Event;

import java.awt.Point;
import java.util.*;
import java.util.stream.IntStream;

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
        var newEvents = new ArrayList<Event>();
        Point insertedClientPosition = MapManager.calculatePositionForNewClient(this, client);
        Point ticketOfficeStep = MapManager.getTicketOfficeQueueStep(this);

        var insertIndex = IntStream.range(0, queue.size())
            .filter(index -> queue.get(index).getPosition().equals(insertedClientPosition))
            .findFirst()
            .orElse(queue.size());

        for (int i = insertIndex; i < queue.size(); i++) {
            var currentClient = queue.get(i);
            currentClient.getPosition().translate(ticketOfficeStep.x, ticketOfficeStep.y);
            newEvents.add(new ClientMovedEvent(currentClient, currentClient.getPosition()));
        }

        client.setPosition(insertedClientPosition);
        queue.add(insertIndex, client);
        newEvents.add(new ClientMovedEvent(client, insertedClientPosition));
        Collections.reverse(newEvents);
        this.events.addAll(newEvents);
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
