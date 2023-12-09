package com.StationManager.generator.policies;

import java.util.Random;

public class RandomPolicy implements Policy{
    private final Double minTime;
    private final Double maxTime;
    private final Random random = new Random();

    public RandomPolicy(Double minTime, Double maxTime) {
        this.minTime = minTime;
        this.maxTime = maxTime;
    }

    @Override
    public Double getSeconds() {
        return minTime + (maxTime - minTime) * random.nextDouble();
    }
}
