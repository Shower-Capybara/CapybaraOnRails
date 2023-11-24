package com.StationManager.app.domain.train_station;

import com.StationManager.app.domain.MapManager;
import com.StationManager.app.domain.client.Client;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class Hall {
    private ArrayList<Segment> entrances;
    private ArrayList<TicketOffice> ticketOffices;
    private Segment size;
    private MapManager mapManager;

    // public Iterable<IEvent> events;

    Hall(Segment size, ArrayList<Segment> entrances, ArrayList<TicketOffice> ticketOffices) {
        this.size = size;
        this.ticketOffices = ticketOffices;
        this.entrances = entrances;
        mapManager.setSize(size);
    }

    public void addTicketOffice(TicketOffice ticketOffice) {
        if (mapManager.IsFree(ticketOffice.getPosition(), entrances, ticketOffices)) {
            ticketOffices.add(ticketOffice);
        } else {
            throw new IllegalStateException("Position is taken");
        }
    }

    public void addClient(Client client) {
        ArrayList<TicketOffice> WorkingTicketOffices =
                ticketOffices.stream()
                        .filter(ticketOffice -> !ticketOffice.getClosed())
                        .collect(Collectors.toCollection(ArrayList::new));

        int size = getSizeOfShortestQueue(WorkingTicketOffices);

        ArrayList<TicketOffice> shortestQueueTicketOffices =
                WorkingTicketOffices.stream()
                        .filter(ticketOffice -> ticketOffice.getQueue().size() == size)
                        .collect(Collectors.toCollection(ArrayList::new));

        mapManager.assignClientToClosestTicketOffice(client, entrances, shortestQueueTicketOffices);
    }

    private static int getSizeOfShortestQueue(ArrayList<TicketOffice> WorkingTicketOffices) {
        if (WorkingTicketOffices.isEmpty()) {
            throw new IllegalStateException("No ticket offices available");
        }

        TicketOffice lowestSizeTicketOffice = WorkingTicketOffices.get(0);
        for (TicketOffice ticketOffice : WorkingTicketOffices) {
            if (ticketOffice.getQueue().size() < lowestSizeTicketOffice.getQueue().size()) {
                lowestSizeTicketOffice = ticketOffice;
            }
        }

        return lowestSizeTicketOffice.getQueue().size();
    }

    public Boolean isCellFree(Segment segment) {
        return mapManager.IsFree(segment, entrances, ticketOffices);
    }

    public ArrayList<Segment> getEntrances() {
        return entrances;
    }

    public ArrayList<TicketOffice> getTicketOffices() {
        return ticketOffices;
    }
}
