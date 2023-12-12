package com.StationManager.app.controllers;

import com.StationManager.app.Settings;
import com.StationManager.app.services.command_listener.Json;
import com.StationManager.app.services.command_listener.RedisPubSub;
import com.StationManager.shared.domain.commands.AddEntranceCommand;
import com.StationManager.shared.domain.commands.AddTicketOfficeCommand;
import com.StationManager.shared.domain.train_station.Segment;
import com.StationManager.shared.domain.train_station.TicketOffice;
import com.StationManager.shared.domain.train_station.TrainStation;
import com.StationManager.shared.services.unitofwork.PostgresUnitOfWork;
import com.StationManager.shared.storage.database.dto.Entrance;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.http.Context;
import redis.clients.jedis.Jedis;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

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
            String filePath = "/Users/erikhpetrushynets/Documents/CapybaraOnRails/backend/station_manager/app/log.txt";
            Path path = Paths.get(filePath);
            List<String> fileLines = Files.readAllLines(path);
            ctx.status(200).json(fileLines);
        } catch (Exception e) {
            ctx.status(500).json("Error getting file, should bu fixed Path!");
        }
    }
//    {
//        "segment": {
//        "start": {
//            "x": 18.0,
//                "y": 20.0
//        },
//        "end": {
//            "x": 20.0,
//                "y": 20.0
//        }
//    },
//        "direction": "Down",
//        "isReserved": false,
//        "timeToServeTicket": 2,
//        "isClosed": false
//    }


    public static void createEntry(Context ctx) {
        // Implementation
        try {
            var hallId = Integer.parseInt(ctx.pathParam("h_id"));
            Segment entrance = objectMapper.readValue(ctx.body(), Segment.class);
            var createCommand = new AddEntranceCommand(entrance, hallId);
            redisPublisher.publish(Settings.getCommandChannel(AddEntranceCommand.class.getSimpleName()), objectMapper.writeValueAsString(createCommand));
            ctx.status(200).result("Entrance created successfully:" + hallId);
        } catch (JsonProcessingException e) {
            ctx.status(400).result("Error processing JSON body");
        }
    }
}

