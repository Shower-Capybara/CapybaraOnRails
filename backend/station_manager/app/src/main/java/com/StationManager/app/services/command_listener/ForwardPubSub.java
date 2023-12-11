package com.StationManager.app.services.command_listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.JedisPubSub;

import static com.StationManager.app.Api.wsSessions;

public class ForwardPubSub extends JedisPubSub {
    public static final Logger logger = LoggerFactory.getLogger(ForwardPubSub.class);

    private static void broadcastMessage(String message) {
        wsSessions.forEach(session -> {
            session.send(message);
        });
    }

    @Override
    public void onPMessage(String pattern, String channel, String message) {
        broadcastMessage(message);
        logger.info(message);
    }
}
