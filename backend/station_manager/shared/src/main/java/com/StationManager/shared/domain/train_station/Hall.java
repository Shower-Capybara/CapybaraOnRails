package com.StationManager.shared.domain.train_station;

import com.StationManager.shared.domain.MapManager;
import com.StationManager.shared.domain.client.Client;
import com.StationManager.shared.domain.events.ClientAddedEvent;
import com.StationManager.shared.domain.events.TicketOfficeAddedEvent;
import com.StationManager.shared.domain.events.Event;

import java.awt.*;
import java.util.List;
import java.util.Queue;
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
        if (entrances.isEmpty()) {
            throw new IllegalArgumentException("Entrances list can not be empty");
        }

        this.id = id;
        this.segment = segment;
        this.ticketOffices = ticketOffices;
        this.entrances = entrances;
    }

    public void addTicketOffice(TicketOffice ticketOffice) {
        if (MapManager.IsSegmentFree(ticketOffice.getSegment(), this)
            && MapManager.TicketOfficeAttachedToSide(ticketOffice, segment)) {
            ticketOffices.add(ticketOffice);
        } else {
            throw new IllegalStateException("Position is incorrect");
        }
        this.events.add(new TicketOfficeAddedEvent(ticketOffice));
    }

    public void addClient(Client client) {
        // split into adding to the hall and adding to the ticket office
        var entrancesPoints = this.entrances
            .stream()
            .map(Segment::getAllPoints)
            .flatMap(Collection::stream)
            .toList();

        this.addClient(client, entrancesPoints.get(new Random().nextInt(entrancesPoints.size())));
    }

    public void addClient(Client client, Point point) {
        client.setPosition(point);
        this.events.add(new ClientAddedEvent(this, client));
    }

    public void assignToTicketOffice(Client client) {
        var workingTicketOffices = ticketOffices.stream()
            .filter(ticketOffice -> !ticketOffice.getClosed())
            .collect(Collectors.toCollection(ArrayList::new));

        int size = getSizeOfShortestQueue(workingTicketOffices);

        var shortestQueueTicketOffices = workingTicketOffices.stream()
            .filter(ticketOffice -> ticketOffice.getQueue().size() == size)
            .collect(Collectors.toCollection(ArrayList::new));

        var closestTicketOffice = MapManager.getClosestTicketOffice(
            client,
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

    public Integer getId() {
        return this.id;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Hall hall)) return false;
        return Objects.equals(id, hall.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}