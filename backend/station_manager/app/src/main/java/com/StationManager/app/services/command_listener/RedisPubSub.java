package com.StationManager.app.services.command_listener;

import com.StationManager.app.Settings;
import com.StationManager.app.services.MessageBus;
import com.StationManager.app.services.unitofwork.InMemoryUnitOfWork;
import com.StationManager.app.services.unitofwork.UnitOfWork;
import com.StationManager.shared.domain.Message;
import com.StationManager.shared.domain.train_station.Direction;
import com.StationManager.shared.domain.train_station.Hall;
import com.StationManager.shared.domain.train_station.Segment;
import com.StationManager.shared.domain.train_station.TicketOffice;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;


public class RedisPubSub extends JedisPubSub {
    private static final Logger logger = LoggerFactory.getLogger(RedisPubSub.class);
    private final Jedis redisPublisher;
    private UnitOfWork uow;

    public RedisPubSub(Jedis redisPublisher) {
        super();
        this.redisPublisher = redisPublisher;

        var ticketOffice = new TicketOffice(1, new Segment(new Point(0, 0), new Point(2, 1)), Direction.Up, 5);
        Hall hall = new Hall(1, new Segment(new Point(0, 0), new Point(10, 10)), List.of(new Segment(new Point(0, 5), new Point(0, 7))), new ArrayList<>());
        hall.addTicketOffice(ticketOffice);
        this.uow = new InMemoryUnitOfWork(); // TODO: replace with PostgresUnitOfWork
        uow.getHallRepository().add(hall);
    }

    @Override
    public void onPMessage(String pattern, String channel, String data) {
        logger.info(String.format("Channel %s received message: %s", channel, data));
        try {
            var objectMapper = Json.getObjectMapper();
            var message = objectMapper.readValue(data, Message.class);

            var events = MessageBus.handle(message, this.uow);
            uow.commit();

            for (var event: events) {
                this.redisPublisher.publish(
                    String.format("%s:%s", Settings.REDIS_EVENTS_CHANNEL, event.getClass().getSimpleName()),
                    objectMapper.writeValueAsString(event)
                );
            }
        } catch (Exception e) {
            logger.error(e.toString());
        }
    }
}
