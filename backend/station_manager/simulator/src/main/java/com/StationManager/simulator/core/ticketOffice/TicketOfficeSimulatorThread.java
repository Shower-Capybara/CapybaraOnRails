package com.StationManager.simulator.core.ticketOffice;

import com.StationManager.simulator.core.ticketOffice.TicketOfficeSimulator;

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
