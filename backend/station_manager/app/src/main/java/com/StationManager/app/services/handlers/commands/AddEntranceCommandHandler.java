package com.StationManager.app.services.handlers.commands;

import com.StationManager.app.Settings;
import com.StationManager.shared.domain.commands.AddEntranceCommand;
import com.StationManager.shared.domain.events.EntranceAddedEvent;
import com.StationManager.shared.domain.train_station.Segment;
import com.StationManager.shared.services.unitofwork.UnitOfWork;
import com.StationManager.simulator.Json;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import redis.clients.jedis.Jedis;

import java.util.List;

public class AddEntranceCommandHandler implements CommandHandler<AddEntranceCommand> {
    Jedis redisPublisher = new Jedis(Settings.REDIS_HOST, Settings.REDIS_PORT);
    private final ObjectMapper objectMapper = Json.getObjectMapper();
    @Override
    public void handle(AddEntranceCommand command, UnitOfWork uow){
        Segment entrance = command.entrance;
        var hallId = command.hallId;
        //uow.getHallRepository().getById(hallId).ifPresent(hall -> hall.setEntrance(entrance));
        EntranceAddedEvent entranceEvent = new EntranceAddedEvent(entrance);
        try{
            redisPublisher.publish(Settings.getEventChannel(EntranceAddedEvent.class.getSimpleName()), objectMapper.writeValueAsString(entranceEvent));
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
    }
}
