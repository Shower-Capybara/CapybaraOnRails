package com.StationManager.generator;

import com.StationManager.generator.policies.IntervalPolicy;
import com.StationManager.generator.simulator.TicketOfficeSimulator;
import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.List;

public class SimulatorManager {
    private List<ClientSimulatorThread> simulatorThreads = new ArrayList<>();
    private Jedis redis;

    public SimulatorManager(Jedis redis) {
        this.redis = redis;
    }

    public void addTicketOffice(Integer ticketOfficeId) {
        var simulator = new TicketOfficeSimulator(
            ticketOfficeId,
            redis
        );
        var thread = new ClientSimulatorThread(simulator);
        this.simulatorThreads.add(thread);
        thread.start();
    }

    public void removeTicketOffice(Integer ticketOfficeId) {
        var simulatorThreadResult = this.simulatorThreads
            .stream()
            .filter(
                simulatorThread -> simulatorThread
                    .getSimulator()
                    .getId()
                    .equals(ticketOfficeId)
            )
            .findFirst();

        if (simulatorThreadResult.isEmpty()) {
            throw new IllegalArgumentException("No simulator for that ticket office found");
        }

        var simulatorThread = simulatorThreadResult.get();
        simulatorThread.interrupt();
    }
}
