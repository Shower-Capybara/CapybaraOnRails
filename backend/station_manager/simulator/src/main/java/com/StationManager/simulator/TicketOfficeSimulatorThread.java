package com.StationManager.simulator;

public class TicketOfficeSimulatorThread extends Thread {
    private final TicketOfficeSimulator simulator;

    public TicketOfficeSimulatorThread(TicketOfficeSimulator simulator) {
        super(simulator);
        this.simulator = simulator;
    }

    public TicketOfficeSimulator getSimulator() {
        return this.simulator;
    }
}
