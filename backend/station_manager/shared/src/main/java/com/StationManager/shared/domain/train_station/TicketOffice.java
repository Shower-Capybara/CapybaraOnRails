package com.StationManager.shared.domain.train_station;

import com.StationManager.shared.domain.MapManager;
import com.StationManager.shared.domain.ServeRecord;
import com.StationManager.shared.domain.client.Client;
import com.StationManager.shared.domain.events.ClientLeftEvent;
import com.StationManager.shared.domain.events.ClientMovedEvent;
import com.StationManager.shared.domain.events.Event;

import java.awt.*;
import java.util.List;
import java.util.Queue;
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
            newEvents.add(
                new ClientMovedEvent(
                    client,
                    client.getPosition(),
                    previousClientPosition
                )
            );
            client.setPosition(previousClientPosition);
            previousClientPosition = currentClientPosition;
        }

        this.events.addAll(newEvents);
    }

    public void addClient(Client client) {
        var newEvents = new ArrayList<Event>();
        Point ticketOfficeStep = MapManager.getTicketOfficeQueueStep(this);

        // initially equal to after the last client point
        var point = queue.isEmpty() ?
            MapManager.GetInitialPoint(this) :
            MapManager.AddPoints(queue.get(queue.size() - 1).getPosition(), ticketOfficeStep);

        var insertIndex = queue.size();

        for (int i = queue.size(); i > 1; i--) {
            var currentClient = queue.get(i - 1);
            if (
                client.getPrivilegy().getSignificance() >
                currentClient.getPrivilegy().getSignificance()
            ) { // current client steps back
                var newPoint = MapManager.AddPoints(currentClient.getPosition(), ticketOfficeStep);
                point = currentClient.getPosition();
                newEvents.add(
                    new ClientMovedEvent(currentClient, currentClient.getPosition(), newPoint)
                );
                currentClient.setPosition(newPoint);
                insertIndex = i - 1;
            } else {
                break;
            }
        }

        // new client takes free position
        newEvents.add(new ClientMovedEvent(client, client.getPosition(), point));
        client.setPosition(point);
        queue.add(insertIndex, client);
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
