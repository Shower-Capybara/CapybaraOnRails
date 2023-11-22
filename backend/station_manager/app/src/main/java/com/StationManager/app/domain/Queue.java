package com.StationManager.app.domain;

import com.StationManager.app.domain.client.Client;

import java.awt.*;
import java.util.LinkedList;

public class Queue {
    private LinkedList<Client> clients;

    public Queue() {
        this.clients = new LinkedList<>();
    }

    public void Add(Client client) {
        clients.add(client);
    }

    public Client Pop() {
        if (clients.isEmpty()) {
            throw new IllegalStateException("Queue is empty, cannot pop");
        }

        Client removedClient = clients.get(0);
        clients.remove(0);

        return removedClient;
    }

    public LinkedList<Client> getClients() {
        return this.clients;
    }

    public Boolean IsEmpty() {
        return clients.isEmpty();
    }

    public Point GetLastClientPoint() {
        return clients.get(clients.size() - 1).getPosition();
    }
}
