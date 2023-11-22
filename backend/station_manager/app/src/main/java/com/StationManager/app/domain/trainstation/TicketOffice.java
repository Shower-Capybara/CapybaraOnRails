package com.StationManager.app.domain.trainstation;

import com.StationManager.app.domain.Queue;
import com.StationManager.app.domain.Transaction;
import com.StationManager.app.domain.client.Client;

import java.awt.Point;

public class TicketOffice {
    private Position position;
    private Queue queue;
    private Integer timeToServeTicket;
    private Boolean isClosed;
    private Boolean isReserved;
    private Direction direction;
    private Iterable<Transaction> transactions;

    // public Iterable<IEvent> events;

    public TicketOffice(Position position, Direction direction) {
        this.position = position;
        this.queue = new Queue();
        this.direction = direction;
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
        Client removedClient = queue.pop();
        Point previousClientPosition = removedClient.getPosition();
        for (Client client : queue.getClients()) {
            Point currentClientPosition = client.getPosition();
            client.setPosition(previousClientPosition);
            previousClientPosition = currentClientPosition;
        }
    }

    public void addClient(Client client) {
        this.queue.add(client);
    }

    public Queue getQueue() {
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
}
