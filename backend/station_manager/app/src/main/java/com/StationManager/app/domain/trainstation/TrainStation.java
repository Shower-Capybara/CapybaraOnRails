package com.StationManager.app.domain.trainstation;

public class TrainStation {
    private Integer id;
    private Hall hall;

    // public Queue<IEvent> events;

    public void SetHall(Hall hall) {
        this.hall = hall;
    }

    public Hall GetHall() {
        return this.hall;
    }
}
