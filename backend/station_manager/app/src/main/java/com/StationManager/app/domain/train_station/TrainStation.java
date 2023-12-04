package com.StationManager.app.domain.train_station;

public class TrainStation {
    private final Integer id;
    private Hall hall;

    public TrainStation(Integer id, Hall hall) {
        this.id = id;
        this.hall = hall;
    }

    public Integer getId() { return this.id; }
    public Hall getHall() { return this.hall; }

    public void setHall(Hall hall) { this.hall = hall; }
}
