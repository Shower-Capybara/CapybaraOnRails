package com.StationManager.app.domain;

import com.StationManager.app.domain.client.Client;

import java.awt.*;
import java.util.ArrayList;

public class Queue {
    private ArrayList<Client> clients;

    public Queue() {
        this.clients = new ArrayList<>();
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

    public ArrayList<Client> getClients() {
        return this.clients;
    }

    public Boolean IsEmpty() {
        return clients.isEmpty();
    }

    public Point GetLastClientPoint() {
        return clients.get(clients.size() - 1).getPosition();
    }
}
