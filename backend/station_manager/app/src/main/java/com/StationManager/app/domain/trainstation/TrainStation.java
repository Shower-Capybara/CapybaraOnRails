package com.StationManager.app.domain.trainstation;

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
}