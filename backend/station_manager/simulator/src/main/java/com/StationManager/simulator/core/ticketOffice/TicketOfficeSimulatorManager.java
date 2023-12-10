package com.StationManager.simulator.core.ticketOffice;

import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TicketOfficeSimulatorManager {
    private final List<TicketOfficeSimulatorThread> simulatorThreads = new ArrayList<>();
    private final Jedis redis;

    public TicketOfficeSimulatorManager(Jedis redis) {
        this.redis = redis;
    }

    private Optional<TicketOfficeSimulatorThread> getSimulatorThread(Integer ticketOfficeId) {
        return this.simulatorThreads
            .stream()
            .filter(
                simulatorThread -> simulatorThread
                    .getSimulator()
                    .getId()
                    .equals(ticketOfficeId)
            )
            .findFirst();
    }

    public void addTicketOffice(Integer ticketOfficeId) {
        this.getSimulatorThread(ticketOfficeId).ifPresent(
            (o) -> {
                throw new IllegalArgumentException(
                    "Ticket office simulator with that id already exists"
                );
            }
        );
        var simulator = new TicketOfficeSimulator(
            ticketOfficeId,
            redis
        );
        var thread = new TicketOfficeSimulatorThread(simulator);
        this.simulatorThreads.add(thread);
        thread.start();
    }

    public void removeTicketOffice(Integer ticketOfficeId) {
        var simulatorThreadResult = getSimulatorThread(ticketOfficeId);
        if (simulatorThreadResult.isEmpty()) {
            throw new IllegalArgumentException("No simulator for that ticket office found");
        }

        var simulatorThread = simulatorThreadResult.get();
        simulatorThread.interrupt();
    }

    public void handleMessage(String message) {
        for (var thread: this.simulatorThreads) {
            thread.getSimulator().publishMessage(message);
        }
    }
}
