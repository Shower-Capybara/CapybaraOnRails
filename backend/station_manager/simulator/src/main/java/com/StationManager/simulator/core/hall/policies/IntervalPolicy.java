package com.StationManager.simulator.core.hall.policies;

public class IntervalPolicy implements Policy{
    private final Double time;

    public IntervalPolicy(Double time) {
        this.time = time;
    }

    @Override
    public Double getSeconds() {
        return time;
    }
}
