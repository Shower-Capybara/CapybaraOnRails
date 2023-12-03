package com.StationManager.app.domain.train_station;

import com.StationManager.app.domain.MapManager;
import com.StationManager.app.domain.client.Client;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.stream.Collectors;

public class Hall {
    private final Integer id;
    private final ArrayList<Segment> entrances;
    private final ArrayList<TicketOffice> ticketOffices;
    private final Segment segment;

    // public Iterable<IEvent> events;

    public Hall(Integer id, Segment segment, ArrayList<Segment> entrances, ArrayList<TicketOffice> ticketOffices) {
        this.id = id;
        this.segment = segment;
        this.ticketOffices = ticketOffices;
        this.entrances = entrances;
    }

    public Integer getId() {
        return this.id;
    }

    public void addTicketOffice(TicketOffice ticketOffice) {
        if (MapManager.IsSegmentFree(ticketOffice.getSegment(), this)) {
            ticketOffices.add(ticketOffice);
        } else {
            throw new IllegalStateException("Position is taken");
        }
    }

    public void addClient(Client client) {
        var WorkingTicketOffices = ticketOffices.stream()
            .filter(ticketOffice -> !ticketOffice.getClosed())
            .collect(Collectors.toCollection(ArrayList::new));

        int size = getSizeOfShortestQueue(WorkingTicketOffices);

        var shortestQueueTicketOffices = WorkingTicketOffices.stream()
            .filter(ticketOffice -> ticketOffice.getQueue().size() == size)
            .collect(Collectors.toCollection(ArrayList::new));

        var closestTicketOffice = MapManager.getClosestTicketOffice(
            client.getPosition(),
            shortestQueueTicketOffices
        );
        closestTicketOffice.addClient(client);
    }

    private static int getSizeOfShortestQueue(ArrayList<TicketOffice> ticketOffices) {
        if (ticketOffices.isEmpty()) {
            throw new IllegalStateException("No ticket offices available");
        }

        var ticketOfficeWithShortestQueue = ticketOffices
            .stream()
            .min(Comparator.comparingInt((ticketOffice) -> ticketOffice.getQueue().size()))
            .get();

        return ticketOfficeWithShortestQueue.getQueue().size();
    }

    public Segment getSegment() {
        return segment;
    }

    public ArrayList<Segment> getEntrances() {
        return entrances;
    }

    public ArrayList<TicketOffice> getTicketOffices() {
        return ticketOffices;
    }
}
