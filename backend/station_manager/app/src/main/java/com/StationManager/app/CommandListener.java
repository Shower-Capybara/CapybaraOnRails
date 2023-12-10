package com.StationManager.app;

import com.StationManager.app.services.MessageBus;
import com.StationManager.app.services.command_listener.RedisPubSub;
import com.StationManager.app.services.handlers.commands.AddClientCommandHandler;
import com.StationManager.shared.domain.commands.AddClientCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;

public class CommandListener {
    private static final Logger logger = LoggerFactory.getLogger(CommandListener.class);

    public static void main(String[] args) {
        var redisListener = new Jedis(Settings.REDIS_HOST, Settings.REDIS_PORT);
        var redisPublisher = new Jedis(Settings.REDIS_HOST, Settings.REDIS_PORT);
        var pubsub = new RedisPubSub(redisPublisher);
        MessageBus.addCommandHandler(AddClientCommand.class, new AddClientCommandHandler());

        logger.info("Command listener startup completed");

        redisListener.subscribe(pubsub, Settings.REDIS_COMMANDS_CHANNEL);
        redisListener.close();
        redisPublisher.close();
    }
}
