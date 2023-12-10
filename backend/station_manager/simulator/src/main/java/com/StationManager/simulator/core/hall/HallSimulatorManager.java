package com.StationManager.simulator.core.hall;

import com.StationManager.simulator.core.hall.policies.IntervalPolicy;
import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class HallSimulatorManager {
    private final List<HallSimulatorThread> simulatorThreads = new ArrayList<>();
    private final Jedis redis;

    public HallSimulatorManager(Jedis redis) {
        this.redis = redis;
    }

    private Optional<HallSimulatorThread> getSimulatorThread(Integer hallId) {
        return this.simulatorThreads
            .stream()
            .filter(
                simulatorThread -> simulatorThread
                    .getSimulator()
                    .getId()
                    .equals(hallId)
            )
            .findFirst();
    }

    public void addHall(Integer hallId) {
        this.getSimulatorThread(hallId).ifPresent(
            (o) -> {
                throw new IllegalArgumentException(
                    "Hall simulator with that id already exists"
                );
            }
        );
        var simulator = new HallSimulator(hallId, new IntervalPolicy(5.0), redis);
        var thread = new HallSimulatorThread(simulator);
        this.simulatorThreads.add(thread);
        thread.start();
    }

    public void removeHall(Integer hallId) {
        var simulatorThreadResult = getSimulatorThread(hallId);
        if (simulatorThreadResult.isEmpty()) {
            throw new IllegalArgumentException("No simulator for that ticket office found");
        }

        var simulatorThread = simulatorThreadResult.get();
        simulatorThread.interrupt();
    }
}
