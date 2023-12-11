package com.StationManager.simulator;

import com.StationManager.simulator.core.ticketOffice.TicketOfficeSimulatorManager;
import com.fasterxml.jackson.core.JsonProcessingException;
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
        try {
            this.simulator.handleMessage(message);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
