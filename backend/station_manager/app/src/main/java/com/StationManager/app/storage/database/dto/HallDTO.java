package com.StationManager.app.storage.database.dto;

import com.StationManager.app.domain.train_station.Segment;
import com.StationManager.app.domain.train_station.TicketOffice;

import java.util.List;

public class HallDTO {
    private Integer id;
    private List<Entrance> entrances;
    private List<HallsTicketOffices> ticketOffices;
    private Segment segment;

    public HallDTO() {

    }

    public HallDTO(Integer id, List<Entrance> entrances, List<HallsTicketOffices> ticketOffices, Segment segment) {
        this.id = id;
        this.entrances = entrances;
        this.ticketOffices = ticketOffices;
        this.segment = segment;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<Entrance> getEntrances() {
        return entrances;
    }

    public void setEntrances(List<Entrance> entrances) {
        this.entrances = entrances;
    }

    public List<HallsTicketOffices> getTicketOffices() {
        return ticketOffices;
    }

    public void setTicketOffices(List<HallsTicketOffices> ticketOffices) {
        this.ticketOffices = ticketOffices;
    }

    public Segment getSegment() {
        return segment;
    }

    public void setSegment(Segment segment) {
        this.segment = segment;
    }
}
