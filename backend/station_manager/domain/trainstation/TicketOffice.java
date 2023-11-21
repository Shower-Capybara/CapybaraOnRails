package trainstation;

import client.Client;

import queue.Queue;

import transaction.Transaction;

public class TicketOffice {
    private Position position;
    private Queue queue;
    private Integer timeToServeTicket;
    private Boolean isClosed;
    private Boolean isReserved;
    private Iterable<Transaction> transactions;

    // public Iterable<IEvent> events;

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
}
