package com.StationManager.shared.domain.train_station;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TrainStation that)) return false;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
