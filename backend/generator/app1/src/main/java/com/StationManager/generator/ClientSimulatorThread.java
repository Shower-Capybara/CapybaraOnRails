package com.StationManager.generator;

import com.StationManager.generator.simulator.TicketOfficeSimulator;

public class ClientSimulatorThread extends Thread {
    private final TicketOfficeSimulator simulator;

    public ClientSimulatorThread(TicketOfficeSimulator simulator) {
        super(simulator);
        this.simulator = simulator;
    }

    public TicketOfficeSimulator getSimulator() {
        return this.simulator;
    }
}
