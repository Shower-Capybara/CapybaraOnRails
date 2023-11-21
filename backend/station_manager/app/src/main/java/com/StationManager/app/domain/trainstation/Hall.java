package com.StationManager.app.domain.trainstation;

import com.StationManager.app.domain.client.Client;

import java.util.ArrayList;

public class Hall {
    private ArrayList<Position> entrances;
    private ArrayList<TicketOffice> ticketOffices;
    private Position size;

    // public Iterable<IEvent> events;

    public void AddTicketOffice(TicketOffice ticketOffice) {
        ticketOffices.add(ticketOffice);
    }

    public void AddClient(Client client) {
        if (ticketOffices.isEmpty()) {
            throw new IllegalStateException("No ticket offices available");
        }

        TicketOffice lowestSizeTicketOffice = ticketOffices.get(0);
        for (TicketOffice ticketOffice : ticketOffices) {
            if (ticketOffice.getQueue().getClients().size()
                    < lowestSizeTicketOffice.getQueue().getClients().size()) {
                lowestSizeTicketOffice = ticketOffice;
            }
        }

        lowestSizeTicketOffice.AddClient(client);
    }

    public Boolean IsCellFree(Position position) {
        throw new UnsupportedOperationException("This method is not yet implemented");
    }
}
