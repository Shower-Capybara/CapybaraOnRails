package com.StationManager.app.controllers;

import com.StationManager.app.Api;
import io.javalin.websocket.WsConfig;
import io.javalin.websocket.WsContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

public class SocketController {
    public static final Logger logger = LoggerFactory.getLogger(Api.class);
    public static Set<WsContext> wsSessions = new HashSet<>();

    public static Consumer<WsConfig> configEvents() {
        return ws -> {
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
        };
    }
}