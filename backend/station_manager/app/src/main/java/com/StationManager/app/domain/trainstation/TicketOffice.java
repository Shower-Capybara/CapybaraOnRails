package com.StationManager.app.domain.trainstation;

import com.StationManager.app.domain.Queue;
import com.StationManager.app.domain.Transaction;
import com.StationManager.app.domain.client.Client;

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

    public void ServeClient(Client client) {
        throw new UnsupportedOperationException("This method is not yet implemented");
    }

    //    public Iterable<IEvent> RemoveClient(){
    //
    //    }

    public void AddClient(Client client) {
        this.queue.Add(client);
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

    public void SetPosition(Position position) {
        this.position = position;
    }

    public void SetClosed(Boolean bool) {
        this.isClosed = bool;
    }

    public Boolean GetClosed() {
        return this.isClosed;
    }
}
