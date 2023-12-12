package com.StationManager.app;

import com.StationManager.app.controllers.HallController;
import com.StationManager.app.controllers.SocketController;
import com.StationManager.app.controllers.TicketOfficeController;
import com.StationManager.app.controllers.TrainStationController;
import com.StationManager.app.services.MessageBus;
import com.StationManager.app.services.command_listener.ForwardPubSub;
import com.StationManager.shared.storage.database.utils.*;
import io.javalin.Javalin;
import redis.clients.jedis.Jedis;

import java.util.logging.Level;

import static io.javalin.apibuilder.ApiBuilder.*;

public class Api {
    public static void main(String[] args) {
        MessageBus.bootstrap();

        Javalin app = Javalin.create().start(Settings.JAVALIN_PORT);
        var redisListener = new Jedis(Settings.REDIS_HOST, Settings.REDIS_PORT);

        app.routes(() -> {
            path("/train_station/{ts_id}", () -> {
                path("", () -> {
                   get(TrainStationController::getState);
                });
                path("/halls/{h_id}", () -> {
                    path("", () -> {
                        get(HallController::getState);
                    });
                    put("/configure_system", HallController::configureSystem);
                    path("/service_history", () -> {
                        get(HallController::getServiceHistory);
                    });
                    path("/ticket_offices", ()->{
                        post(TicketOfficeController::createTicketOffice);
                        path("/{t_id}/open", () ->
                            post(TicketOfficeController::openTicketOffice));
                        path("/{t_id}/close", () ->
                            post(TicketOfficeController::closeTicketOffice));
                    });
                });
            });
            ws("/events", SocketController.configEvents());
        });

        redisListener.psubscribe(new ForwardPubSub(SocketController.wsSessions), Settings.getEventChannel("*"));
        HibernateUtil.getSessionFactory().close();
    }
}