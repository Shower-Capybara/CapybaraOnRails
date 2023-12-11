package com.StationManager.app.controllers;

import com.StationManager.app.Settings;
import com.StationManager.app.services.command_listener.Json;
import com.StationManager.app.services.command_listener.RedisPubSub;
import com.StationManager.shared.domain.commands.CloseTicketOfficeCommand;
import com.StationManager.shared.domain.commands.OpenTicketOfficeCommand;
import com.StationManager.shared.domain.commands.StopGeneratorCommand;
import com.StationManager.shared.domain.events.ClientServedEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.http.Context;
import redis.clients.jedis.Jedis;

public class TicketOfficeController {
    static Jedis redisPublisher = new Jedis(Settings.REDIS_HOST, Settings.REDIS_PORT);
    static ObjectMapper objectMapper = Json.getObjectMapper();


    public static void createTicketOffice(Context ctx) {
        // Implementation
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
