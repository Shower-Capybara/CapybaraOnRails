package com.StationManager.generator.policies;

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
