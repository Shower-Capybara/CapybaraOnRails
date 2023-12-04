package com.StationManager.app.domain.train_station;

import com.StationManager.app.domain.MapManager;
import com.StationManager.app.domain.client.Client;
import com.StationManager.app.domain.events.Event;
import com.StationManager.app.domain.events.TicketOfficeAddedEvent;

import java.util.*;
import java.util.stream.Collectors;

public class Hall {
    private final Integer id;
    private final List<Segment> entrances;
    private final List<TicketOffice> ticketOffices;
    private final Segment segment;

    public final Queue<Event> events = new LinkedList<>();

    public Hall(
        Integer id,
        Segment segment,
        List<Segment> entrances,
        List<TicketOffice> ticketOffices
    ) {
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
        this.events.add(new TicketOfficeAddedEvent(ticketOffice));
    }

    public void addClient(Client client) {
        // split into adding to the hall and adding to the ticket office
        var workingTicketOffices = ticketOffices.stream()
            .filter(ticketOffice -> !ticketOffice.getClosed())
            .collect(Collectors.toCollection(ArrayList::new));

        int size = getSizeOfShortestQueue(workingTicketOffices);

        var shortestQueueTicketOffices = workingTicketOffices.stream()
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
            .min(Comparator.comparingInt(ticketOffice -> ticketOffice.getQueue().size()))
            .get();

        return ticketOfficeWithShortestQueue.getQueue().size();
    }

    public Segment getSegment() {
        return segment;
    }

    public List<Segment> getEntrances() {
        return entrances;
    }

    public List<TicketOffice> getTicketOffices() {
        return ticketOffices;
    }
}
