package com.StationManager.app;

import com.StationManager.app.services.MessageBus;
import io.javalin.Javalin;
import io.javalin.websocket.WsContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class Api {
    private static final Logger logger = LoggerFactory.getLogger(Api.class);
    static Set<WsContext> wsSessions = new HashSet<>();

    public static void main(String[] args) {
        MessageBus.bootstrap();

        Javalin app = Javalin.create().start(Settings.JAVALIN_PORT);
        var redisListener = new Jedis(Settings.REDIS_HOST, Settings.REDIS_PORT);

        app.get("/", ctx -> {
            logger.info("Request finished");
        });
        app.ws("/events", ws -> {
            ws.onConnect(ctx -> {
                wsSessions.add(ctx);
                ctx.enableAutomaticPings(10000, TimeUnit.MILLISECONDS);
                logger.info("Established: " + ctx.getSessionId());
            });
            ws.onMessage(ctx -> {
                logger.info("Message received: " + ctx.getSessionId());
            });
            ws.onClose(ctx -> {
                wsSessions.remove(ctx);
                logger.info("Closed: " + ctx.getSessionId());
            });
            ws.onError(ctx -> logger.info("Error " + ctx.getSessionId()));
        });
        redisListener.psubscribe(new JedisPubSub() {
            @Override
            public void onPMessage(String pattern, String channel, String message) {
                broadcastMessage(message);
                logger.info(message);
            }
        }, "station_manager:events:*");
    }

    private static void broadcastMessage(String message) {
        wsSessions.forEach(session -> {
            session.send(message);
        });
    }
}
