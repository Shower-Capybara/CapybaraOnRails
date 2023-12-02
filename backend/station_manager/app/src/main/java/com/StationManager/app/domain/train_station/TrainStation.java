package com.StationManager.app.domain.train_station;

public class TrainStation {
    private Integer id;
    private Hall hall;

    // public Queue<IEvent> events;

    public void setHall(Hall hall) {
        this.hall = hall;
    }

    public Hall getHall() {
        return this.hall;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
