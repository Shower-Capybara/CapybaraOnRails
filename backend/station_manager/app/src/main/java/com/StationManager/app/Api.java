package com.StationManager.app;

import com.StationManager.app.controllers.HallController;
import com.StationManager.app.controllers.SocketController;
import com.StationManager.app.controllers.TicketOfficeController;
import com.StationManager.app.services.MessageBus;
import com.StationManager.app.services.command_listener.ForwardPubSub;
import io.javalin.Javalin;
import io.javalin.websocket.WsContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;

import java.util.HashSet;
import java.util.Set;

import static io.javalin.apibuilder.ApiBuilder.*;

public class Api {
    public static final Logger logger = LoggerFactory.getLogger(Api.class);
    public static Set<WsContext> wsSessions = new HashSet<>();

    public static void main(String[] args) {
        MessageBus.bootstrap();

        Javalin app = Javalin.create().start(Settings.JAVALIN_PORT);
        var redisListener = new Jedis(Settings.REDIS_HOST, Settings.REDIS_PORT);

        app.routes(() -> {
            path("/train_station", () -> {
                path("/hall/{h_id}", () -> {
                    get("/get-state", HallController::getState);
                    put("/save-state", HallController::saveState);
                    put("/configure-system", HallController::configureSystem);
                    get("/get-service-history", HallController::getServiceHistory);
                    post("/create-entry", HallController::createEntry);
                    post("/create-ticket-office", TicketOfficeController::createTicketOffice);
                    path("/open-ticket-office/{t_id}", () ->
                        put(TicketOfficeController::openTicketOffice));
                    path("/close-ticket-office/{t_id}", () ->
                        put(TicketOfficeController::closeTicketOffice));
                });
            });
            ws("/events", SocketController.configEvents());
        });

        redisListener.psubscribe(new ForwardPubSub(), Settings.getEventChannel("*"));
    }
}




