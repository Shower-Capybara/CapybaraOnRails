package com.StationManager.app.services.command_listener;

import io.javalin.websocket.WsContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.JedisPubSub;

import java.util.Set;

public class ForwardPubSub extends JedisPubSub {
    public static final Logger logger = LoggerFactory.getLogger(ForwardPubSub.class);
    static Set<WsContext> wsSessions;

    public ForwardPubSub(Set<WsContext> wsSessions) {
        ForwardPubSub.wsSessions = wsSessions;
    }

    private void broadcastMessage(String message) {
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
