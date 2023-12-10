package com.StationManager.simulator.core.hall;

import com.StationManager.shared.domain.client.Client;
import com.StationManager.shared.domain.commands.AddClientCommand;
import com.StationManager.simulator.Json;
import com.StationManager.simulator.Settings;
import com.StationManager.simulator.core.hall.policies.Policy;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;

import java.util.HashMap;

public class HallSimulator implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(HallSimulator.class);

    private final Integer id;
    private Policy schedulingPolicy;
    private final Jedis redis;
    private final Gson gson = Json.getGson();

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
            HashMap<String, Object> message = new HashMap<>();
            message.put("type", "AddClientCommand");
            message.put("payload", command);

            try {
                this.redis.publish(Settings.REDIS_COMMANDS_CHANNEL, gson.toJson(message));
                logger.info("Sent AddClientCommand to Redis");
                Thread.sleep(Math.round(this.schedulingPolicy.getSeconds() * 1000));
            } catch (InterruptedException e) {
                break;
            }
        }
    }

    public Integer getId() { return this.id; }

    public void setPolicy(Policy schedulingPolicy) { this.schedulingPolicy = schedulingPolicy; }
}
