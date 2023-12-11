package com.StationManager.app.controllers;

import com.StationManager.app.services.command_listener.Json;
import com.StationManager.shared.services.unitofwork.PostgresUnitOfWork;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.http.Context;

public class TrainStationController {
    public static ObjectMapper objectMapper = Json.getObjectMapper();

    public static void getState(Context ctx) {
        var trainStationId = Integer.parseInt(ctx.pathParam("ts_id"));

        try (var uow = new PostgresUnitOfWork()) {
            var tsResult = uow.getTrainStationRepository().getById(trainStationId);

            if (tsResult.isEmpty()) {
                ctx.status(404);
                return;
            }

            var ts = tsResult.get();
            try {
                ctx
                    .status(200)
                    .contentType("application/json")
                    .result(objectMapper.writeValueAsString(ts));
            } catch (JsonProcessingException e) {
                ctx.status(500);
            }
        }
    }
}

