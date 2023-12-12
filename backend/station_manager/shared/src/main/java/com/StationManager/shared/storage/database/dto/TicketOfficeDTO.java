package com.StationManager.shared.storage.database.dto;

import com.StationManager.shared.domain.train_station.Direction;
import com.StationManager.shared.domain.train_station.Segment;

import java.util.LinkedList;
import java.util.List;

public class TicketOfficeDTO {
    private Integer id;
    private Segment segment;
    private List<ClientPositions> queuePositions = new LinkedList<>();
    private Integer timeToServeTicket;
    private Boolean isClosed;
    private Boolean isReserved;
    private Direction direction;

    public TicketOfficeDTO() {
    }

    public TicketOfficeDTO(Integer timeToServeTicket, Boolean isClosed, Boolean isReserved) {
        this.timeToServeTicket = timeToServeTicket;
        this.isClosed = isClosed;
        this.isReserved = isReserved;
    }

    public TicketOfficeDTO(
            Integer id,
            Segment segment,
            Direction direction,
            Integer timeToServeTicket
    ) {
        this.id = id;
        this.segment = segment;
        this.timeToServeTicket = timeToServeTicket;
        this.isClosed = false;
        this.isReserved = false;
        this.direction = direction;
    }

    public Segment getSegment() {
        return segment;
    }

    public void setSegment(Segment segment) {
        this.segment = segment;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public void addClient(ClientPositions clientPositions) {
        queuePositions.add(clientPositions);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<ClientPositions> getQueuePositions() {
        return queuePositions;
    }

    public void setQueuePositions(List<ClientPositions> queuePositions) {
        this.queuePositions = queuePositions;
    }

    public Integer getTimeToServeTicket() {
        return timeToServeTicket;
    }

    public void setTimeToServeTicket(Integer timeToServeTicket) {
        this.timeToServeTicket = timeToServeTicket;
    }

    public Boolean getIsClosed() {
        return isClosed;
    }

    public void setIsClosed(Boolean closed) {
        isClosed = closed;
    }

    public Boolean getIsReserved() {
        return isReserved;
    }

    public void setIsReserved(Boolean reserved) {
        isReserved = reserved;
    }
}
