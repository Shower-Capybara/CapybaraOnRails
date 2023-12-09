package com.StationManager.generator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        var redis = new Jedis(Settings.REDIS_HOST, Settings.REDIS_PORT);
        var simulator = new SimulatorManager(redis);
        simulator.addTicketOffice(1);

        logger.info("App startup completed");

        while (true) { // replace with listening for ClientBeingServed event
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                break;
            }
        }

        redis.close();
    }
}
