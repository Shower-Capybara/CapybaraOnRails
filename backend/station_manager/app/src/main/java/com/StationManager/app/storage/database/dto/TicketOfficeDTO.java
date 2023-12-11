package com.StationManager.app.storage.database.dto;

import com.StationManager.app.domain.train_station.Direction;
import com.StationManager.app.domain.train_station.Segment;
import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

//@Entity
//@Table(name = "ticket_offices")
public class TicketOfficeDTO {
//    @Id
//    @GenericGenerator(name="kaugen" , strategy="increment")
//    @GeneratedValue(generator="kaugen")
//    @Column(name = "id")
    private Integer id;

    public Segment getSegment() {
        return segment;
    }

    public void setSegment(Segment segment) {
        this.segment = segment;
    }

    private Segment segment;

//    @OneToMany(mappedBy = "ticketOffice", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ClientPositions> queuePositions = new LinkedList<>();

//    @Column(name = "time_to_serve_ticket")
    private Integer timeToServeTicket;

//    @Column(name = "is_closed")
    private Boolean isClosed;

//    @Column(name = "is_reserved")
    private Boolean isReserved;

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

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

    public void addClient(ClientPositions clientPositions) {
        queuePositions.add(clientPositions);
//        clientPositions.setTicketOffice(this);
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
//        this.queuePositions.clear();
//        this.queuePositions.addAll(queuePositions);
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
