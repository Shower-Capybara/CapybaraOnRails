package com.StationManager.simulator;

import com.StationManager.simulator.core.hall.HallSimulatorManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;

public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        var redis = new Jedis(Settings.REDIS_HOST, Settings.REDIS_PORT);
        var listenerRedis = new Jedis(Settings.REDIS_HOST, Settings.REDIS_PORT);

        var simulator = new SimulatorManager(redis);
        var hallSimulator = new HallSimulatorManager(redis);
        simulator.addTicketOffice(1);
        hallSimulator.addHall(1);

        logger.info("App startup completed");

        var pubsub = new RedisPubSub(simulator);
        listenerRedis.subscribe(pubsub, Settings.REDIS_EVENTS_CHANNEL);
        redis.close();
        listenerRedis.close();
    }
}
