package com.StationManager.simulator.core.hall;

public class HallSimulatorThread extends Thread {
    private final HallSimulator simulator;

    public HallSimulatorThread(HallSimulator simulator) {
        super(simulator);
        this.simulator = simulator;
    }

    public HallSimulator getSimulator() {
        return this.simulator;
    }
}
