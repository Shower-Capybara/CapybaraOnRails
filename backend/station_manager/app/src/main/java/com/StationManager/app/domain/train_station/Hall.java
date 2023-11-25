package com.StationManager.app.domain.train_station;

import com.StationManager.app.domain.MapManager;
import com.StationManager.app.domain.client.Client;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class Hall {
    private ArrayList<Segment> entrances;
    private ArrayList<TicketOffice> ticketOffices;
    private Segment size;

    // public Iterable<IEvent> events;

    Hall(Segment size, ArrayList<Segment> entrances, ArrayList<TicketOffice> ticketOffices) {
        this.size = size;
        this.ticketOffices = ticketOffices;
        this.entrances = entrances;
        MapManager.setSize(size);
    }

    public void addTicketOffice(TicketOffice ticketOffice) {
        if (MapManager.IsFree(ticketOffice.getSegment(), entrances, ticketOffices)) {
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

        var closestTicketOffice = MapManager.getClosestTicketOffice(client.getPosition(), shortestQueueTicketOffices);
        if (closestTicketOffice != null) closestTicketOffice.addClient(client);
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
        return MapManager.IsFree(segment, entrances, ticketOffices);
    }

    public ArrayList<Segment> getEntrances() {
        return entrances;
    }

    public ArrayList<TicketOffice> getTicketOffices() {
        return ticketOffices;
    }
}
