package com.StationManager.app.controllers;

import com.StationManager.app.Settings;
import com.StationManager.app.services.MessageBus;
import com.StationManager.app.services.command_listener.Json;
import com.StationManager.app.services.command_listener.RedisPubSub;
import com.StationManager.shared.domain.commands.AddTicketOfficeCommand;
import com.StationManager.shared.domain.commands.CloseTicketOfficeCommand;
import com.StationManager.shared.domain.commands.OpenTicketOfficeCommand;
import com.StationManager.shared.domain.commands.StopGeneratorCommand;
import com.StationManager.shared.domain.events.ClientServedEvent;
import com.StationManager.shared.domain.train_station.TicketOffice;
import com.StationManager.shared.services.unitofwork.PostgresUnitOfWork;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.http.Context;
import redis.clients.jedis.Jedis;

public class TicketOfficeController {
    static Jedis redisPublisher = new Jedis(Settings.REDIS_HOST, Settings.REDIS_PORT);
    static ObjectMapper objectMapper = Json.getObjectMapper();


    public static void createTicketOffice(Context ctx) {
        var hallId = Integer.parseInt(ctx.pathParam("h_id"));
        var body = ctx.body();
        TicketOffice ticketOffice;

        try {
            ticketOffice = objectMapper.readValue(body, TicketOffice.class);
        } catch (JsonProcessingException e) {
            ctx.status(422);
            return;
        }

        var command = new AddTicketOfficeCommand(ticketOffice, hallId);
        try (var uow = new PostgresUnitOfWork()) {
            MessageBus.handle(command, uow);
            uow.commit();
        }
        ctx.status(200).result("Ticket office created successfully");
    }

    public static void closeTicketOffice(Context ctx) throws JsonProcessingException {
        var ticketOfficeId = Integer.parseInt(ctx.pathParam("t_id"));
        var closeTicketOfficeCommand = new CloseTicketOfficeCommand(ticketOfficeId);
        redisPublisher.publish(
            Settings.getCommandChannel(CloseTicketOfficeCommand.class.getSimpleName()),
            objectMapper.writeValueAsString(closeTicketOfficeCommand)
        );
        ctx.status(201);
    }

    public static void openTicketOffice(Context ctx) throws JsonProcessingException {
        var ticketOfficeId = Integer.parseInt(ctx.pathParam("t_id"));
        var openTicketOfficeCommand = new OpenTicketOfficeCommand(ticketOfficeId);
        redisPublisher.publish(
            Settings.getCommandChannel(OpenTicketOfficeCommand.class.getSimpleName()),
            objectMapper.writeValueAsString(openTicketOfficeCommand)
        );
        ctx.status(201);
    }
}
