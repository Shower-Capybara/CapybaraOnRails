package com.StationManager.app.domain.train_station;

import com.StationManager.app.domain.MapManager;
import com.StationManager.app.domain.client.Client;
import com.StationManager.app.domain.events.ClientAddedEvent;
import com.StationManager.app.domain.events.Event;
import com.StationManager.app.domain.events.TicketOfficeAddedEvent;

import java.awt.*;
import java.util.List;
import java.util.Queue;
import java.util.*;
import java.util.stream.Collectors;

public class Hall {
    public final Queue<Event> events = new LinkedList<>();
    private Integer id;
    private List<Segment> entrances;
    private List<TicketOffice> ticketOffices;
    private Segment segment;

    public Hall() {
        this.entrances = new ArrayList<>();
        this.ticketOffices = new ArrayList<>();
    }

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
                .filter(ticketOffice -> !ticketOffice.getIsClosed())
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

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Segment getSegment() {
        return segment;
    }

    public void setSegment(Segment segment) {
        this.segment = segment;
    }

    public List<Segment> getEntrances() {
        return entrances;
    }

    public void setEntrances(List<Segment> entrances) {
        this.entrances = entrances;
    }

    public List<TicketOffice> getTicketOffices() {
        return ticketOffices;
    }

    public void setTicketOffices(List<TicketOffice> ticketOffices) {
        this.ticketOffices = ticketOffices;
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
