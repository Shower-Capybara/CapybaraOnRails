package com.StationManager.app.controllers;

import com.StationManager.app.Settings;
import com.StationManager.app.services.command_listener.Json;
import com.StationManager.shared.domain.commands.AddEntranceCommand;
import com.StationManager.shared.domain.commands.AddTicketOfficeCommand;
import com.StationManager.shared.domain.train_station.Segment;
import com.StationManager.shared.services.unitofwork.PostgresUnitOfWork;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.http.Context;
import redis.clients.jedis.Jedis;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class HallController {
    public static ObjectMapper objectMapper = Json.getObjectMapper();
    public static Jedis redisPublisher = new Jedis(Settings.REDIS_HOST, Settings.REDIS_PORT);

    public static void getState(Context ctx) {
        var hallId = Integer.parseInt(ctx.pathParam("h_id"));
        try (var uow = new PostgresUnitOfWork()) {
            var hallResult = uow.getHallRepository().getById(hallId);

            if (hallResult.isEmpty()) {
                ctx.status(404);
                return;
            }
            var hall = hallResult.get();
            try {
                ctx
                    .status(200)
                    .contentType("application/json")
                    .result(objectMapper.writeValueAsString(hall));
            } catch (JsonProcessingException e) {
                ctx.status(500);
            }
        }
    }

    public static void configureSystem(Context ctx) {
        // Implementation
        ctx.status(200).result("System configured successfully");
    }

    public static void getServiceHistory(Context ctx) {
        try {
            String filePath = "log.txt";
            Path path = Paths.get(filePath);
            List<String> fileLines = Files.readAllLines(path);
            ctx.status(200).json(fileLines);
        } catch (Exception e) {
            ctx.status(500).json("Error getting file, path should bu fixed!");
        }
    }

    public static void createEntry(Context ctx) {
        // Implementation
        try {
            var hallId = Integer.parseInt(ctx.pathParam("h_id"));
            Segment entrance = objectMapper.readValue(ctx.body(), Segment.class);
            var createCommand = new AddEntranceCommand(entrance, hallId);
            redisPublisher.publish(Settings.getCommandChannel(AddTicketOfficeCommand.class.getSimpleName()), objectMapper.writeValueAsString(createCommand));
            ctx.status(200).result("Entrance created successfully");
        } catch (JsonProcessingException e) {
            ctx.status(400).result("Error processing JSON body");
        }
    }
}

