package com.StationManager.app.domain.train_station;

import com.StationManager.app.domain.MapManager;
import com.StationManager.app.domain.client.Client;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class Hall {
    private ArrayList<Position> entrances;
    private ArrayList<TicketOffice> ticketOffices;
    private Position size;
    private MapManager mapManager;

    // public Iterable<IEvent> events;

    Hall(Position size, ArrayList<Position> entrances, ArrayList<TicketOffice> ticketOffices) {
        this.size = size;
        this.ticketOffices = ticketOffices;
        this.entrances = entrances;
        mapManager = new MapManager(size, this);
    }

    public void addTicketOffice(TicketOffice ticketOffice) {
        if (mapManager.positionIsFree(ticketOffice.getPosition())) {
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

        int size = getSizeOfSmallestQueue(WorkingTicketOffices);

        ArrayList<TicketOffice> smallestTicketOffices =
                WorkingTicketOffices.stream()
                        .filter(ticketOffice -> ticketOffice.getQueue().size() == size)
                        .collect(Collectors.toCollection(ArrayList::new));

        mapManager.assignClientToClosestTicketOffice(client, smallestTicketOffices);
    }

    private static int getSizeOfSmallestQueue(ArrayList<TicketOffice> WorkingTicketOffices) {
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

    public Boolean isCellFree(Position position) {
        return mapManager.positionIsFree(position);
    }

    public ArrayList<Position> getEntrances() {
        return entrances;
    }

    public ArrayList<TicketOffice> getTicketOffices() {
        return ticketOffices;
    }
}
