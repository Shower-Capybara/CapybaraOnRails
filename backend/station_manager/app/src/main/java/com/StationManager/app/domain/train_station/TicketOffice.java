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
        Point ticketOfficeStep = MapManager.getTicketOfficeQueueStep(this);

        /* Setting client position and adding it to queue if queue is empty (position is initial point). */
        if (queue.isEmpty()){
            client.setPosition(MapManager.GetInitialPoint(this));

            newEvents.add(new ClientMovedEvent(client, client.getPosition()));

            queue.add(client);
            return;
        }

        /*
         * Setting client position if queue has at least 2 items.
         * The list is processed in reverse order as required by the frontend.
         * Element 0 is not changed.
         */
        for (int i = queue.size(); i > 1; i--) {
            var currentClient = queue.get(i - 1);

            var temp = new Point(currentClient.getPosition());
            temp.translate(ticketOfficeStep.x, ticketOfficeStep.y);

            /*
             * If new client privilegy significance is higher than current, current is moved one step.
             * Else - new client is set to position one step after current.
             */
            if (client.getPrivilegy().getSignificance() > currentClient.getPrivilegy().getSignificance()) {
                currentClient.setPosition(temp);
                newEvents.add(new ClientMovedEvent(currentClient, currentClient.getPosition()));
            } else {
                client.setPosition(temp);
                newEvents.add(new ClientMovedEvent(client, client.getPosition()));
                queue.add(i, client);
                return;
            }
        }

        /* Setting client position if queue has 1 element (position is initial point plus step). */
        var temp = MapManager.GetInitialPoint(this);
        temp.translate(ticketOfficeStep.x, ticketOfficeStep.y);
        client.setPosition(temp);

        newEvents.add(new ClientMovedEvent(client, client.getPosition()));

        queue.add(1, client);

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
