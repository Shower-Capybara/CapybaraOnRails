package com.StationManager.simulator.core.hall;

import com.StationManager.shared.domain.client.Client;
import com.StationManager.shared.domain.commands.AddClientCommand;
import com.StationManager.simulator.Json;
import com.StationManager.simulator.Settings;
import com.StationManager.simulator.core.hall.policies.Policy;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;

public class HallSimulator implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(HallSimulator.class);

    private final Integer id;
    private Policy schedulingPolicy;
    private final Jedis redis;
    private final ObjectMapper objectMapper = Json.getObjectMapper();

    public HallSimulator(Integer id, Policy schedulingPolicy, Jedis redis) {
        this.id = id;
        this.schedulingPolicy = schedulingPolicy;
        this.redis = redis;
    }

    private Client generateClient() {
        return new Client(
            null,
            "First name", // change to random
            "Last name", // change to random
            null, // change to random
            null
        );
    }

    @Override
    public void run() {
        while (true) {
            // TODO: check if the hall is not exceeding the capacity norm
            var client = this.generateClient();
            var command = new AddClientCommand(client, this.id);

            try {
                var channel = String.format(
                    "%s:%s",
                    Settings.REDIS_COMMANDS_CHANNEL,
                    command.getClass().getSimpleName()
                );
                this.redis.publish(channel, this.objectMapper.writeValueAsString(command));
                logger.info(String.format("Sent AddClientCommand to %s channel", channel));
                Thread.sleep(Math.round(this.schedulingPolicy.getSeconds() * 1000));
            } catch (InterruptedException | JsonProcessingException e) {
                break;
            }
        }
    }

    public Integer getId() { return this.id; }

    public void setPolicy(Policy schedulingPolicy) { this.schedulingPolicy = schedulingPolicy; }
}
