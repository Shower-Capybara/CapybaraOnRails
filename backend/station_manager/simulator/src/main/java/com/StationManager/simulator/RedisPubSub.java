package com.StationManager.simulator;

import com.StationManager.simulator.core.ticketOffice.TicketOfficeSimulatorManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.JedisPubSub;

public class RedisPubSub extends JedisPubSub {
    private static final Logger logger = LoggerFactory.getLogger(RedisPubSub.class);

    private final TicketOfficeSimulatorManager simulator;

    public RedisPubSub(TicketOfficeSimulatorManager simulator) {
        super();
        this.simulator = simulator;
    }

    @Override
    public void onMessage(String channel, String message) {
        logger.info(String.format("Channel %s received message: %s", channel, message));
        this.simulator.handleMessage(message);
    }
}
