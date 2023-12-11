package com.StationManager.app.storage.database.dto;

import com.StationManager.app.domain.train_station.Hall;
import com.StationManager.app.domain.train_station.Segment;

public class Entrance {
    private Integer id;
    private HallDTO hall;
    private Segment segment;
    private Integer index;

    public Entrance() {
    }

    public Entrance(HallDTO hallId, Segment segment) {
        this.hall = hallId;
        this.segment = segment;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public HallDTO getHall() {
        return hall;
    }

    public void setHall(HallDTO hall) {
        this.hall = hall;
    }

    public void setSegment(Segment segment) {
        this.segment = segment;
    }

    public Integer getId() {
        return id;
    }

    public Segment getSegment() {
        return segment;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }
}
