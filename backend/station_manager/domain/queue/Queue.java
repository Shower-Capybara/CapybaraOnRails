package queue;

import client.Client;

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
}
