package com.StationManager.app;

import com.StationManager.app.controllers.HallController;
import com.StationManager.app.controllers.SocketController;
import com.StationManager.app.controllers.TicketOfficeController;
import com.StationManager.app.services.MessageBus;
import com.StationManager.app.services.command_listener.ForwardPubSub;
import io.javalin.Javalin;
import redis.clients.jedis.Jedis;

import static io.javalin.apibuilder.ApiBuilder.*;

public class Api {
    public static void main(String[] args) {
        MessageBus.bootstrap();

        Javalin app = Javalin.create().start(Settings.JAVALIN_PORT);
        var redisListener = new Jedis(Settings.REDIS_HOST, Settings.REDIS_PORT);

        app.routes(() -> {
            path("/train_station", () -> {
                path("/halls/{h_id}", () -> {
                    path("/state", () -> {
                        get(HallController::getState);
                        put(HallController::saveState);
                    });
                    put("/configure_system", HallController::configureSystem);
                    path("/service_history", () -> {
                        get(HallController::getServiceHistory);
                    });
                    path("/entry", () -> {
                        post(HallController::createEntry);
                    });
                    path("/ticket_offices", ()->{
                        post(TicketOfficeController::createTicketOffice);
                        path("/{t_id}/open", () ->
                            put(TicketOfficeController::openTicketOffice));
                        path("/{t_id}/close", () ->
                            put(TicketOfficeController::closeTicketOffice));
                    });
                });
            });
            ws("/events", SocketController.configEvents());
        });

        redisListener.psubscribe(new ForwardPubSub(SocketController.wsSessions), Settings.getEventChannel("*"));
    }
}