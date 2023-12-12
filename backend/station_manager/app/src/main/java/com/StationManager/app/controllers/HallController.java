package com.StationManager.app.controllers;

import com.StationManager.app.services.command_listener.Json;
import com.StationManager.shared.services.unitofwork.PostgresUnitOfWork;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.http.Context;

public class HallController {
    public static ObjectMapper objectMapper = Json.getObjectMapper();
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
        // Implementation
        ctx.status(200).result("Service history information");
    }

    public static void createTicketOffice(Context ctx) {
        // Implementation
        ctx.status(200).result("Ticket office created successfully");
    }

    public static void createEntry(Context ctx) {
        // Implementation
        ctx.status(200).result("Entry created successfully");
    }
}

