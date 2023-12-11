package com.StationManager.app.controllers;

import io.javalin.websocket.WsConfig;

import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

import static com.StationManager.app.Api.logger;
import static com.StationManager.app.Api.wsSessions;

public class SocketController {
    public static  Consumer<WsConfig> configEvents() {
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