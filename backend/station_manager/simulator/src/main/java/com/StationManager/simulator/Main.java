package com.StationManager.simulator;

import com.StationManager.shared.domain.events.ClientBeingServedEvent;
import com.StationManager.shared.storage.database.utils.HibernateUtil;
import com.StationManager.simulator.core.hall.HallSimulatorManager;
import com.StationManager.simulator.core.ticketOffice.TicketOfficeSimulatorManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;

public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        var redis = new Jedis(Settings.REDIS_HOST, Settings.REDIS_PORT);
        var listenerRedis = new Jedis(Settings.REDIS_HOST, Settings.REDIS_PORT);

        var simulator = new TicketOfficeSimulatorManager(redis);
        var hallSimulator = new HallSimulatorManager(redis);
        simulator.addTicketOffice(1);
        hallSimulator.addHall(1);

        logger.info("App startup completed");

        var pubsub = new RedisPubSub(simulator);
        listenerRedis.subscribe(
            pubsub,
            Settings.getCommandChannel("CloseTicketOfficeCommand"),
            Settings.getCommandChannel("OpenTicketOfficeCommand"),
            Settings.getEventChannel(ClientBeingServedEvent.class.getSimpleName())
        );

        redis.close();
        listenerRedis.close();
        HibernateUtil.getSessionFactory().close();
    }
}
