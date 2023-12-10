package com.StationManager.app;

import com.StationManager.app.services.MessageBus;
import com.StationManager.app.services.command_listener.RedisPubSub;
import com.StationManager.shared.domain.events.ClientServedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;

public class MessageListener {
    private static final Logger logger = LoggerFactory.getLogger(MessageListener.class);

    public static void main(String[] args) {
        var redisListener = new Jedis(Settings.REDIS_HOST, Settings.REDIS_PORT);
        var redisPublisher = new Jedis(Settings.REDIS_HOST, Settings.REDIS_PORT);
        var pubsub = new RedisPubSub(redisPublisher);
        MessageBus.bootstrap();

        logger.info("Command listener startup completed");

        redisListener.psubscribe(
            pubsub,
            String.format("%s:*", Settings.REDIS_COMMANDS_CHANNEL),
            String.format(
                "%s:%s",
                Settings.REDIS_EVENTS_CHANNEL,
                ClientServedEvent.class.getSimpleName()
            )
        );
        redisListener.close();
        redisPublisher.close();
    }
}
