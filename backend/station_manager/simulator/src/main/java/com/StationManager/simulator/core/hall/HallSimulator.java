package com.StationManager.simulator.core.hall;

import com.StationManager.shared.domain.client.Client;
import com.StationManager.shared.domain.client.Privilegy;
import com.StationManager.shared.domain.commands.AddClientCommand;
import com.StationManager.shared.services.unitofwork.PostgresUnitOfWork;
import com.StationManager.simulator.Json;
import com.StationManager.simulator.Settings;
import com.StationManager.simulator.core.hall.policies.Policy;
import com.StationManager.simulator.core.hall.policies.RandomPolicy;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;

import java.util.List;
import java.util.UUID;

public class HallSimulator implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(HallSimulator.class);

    private final Integer id;
    private final Integer maxCapacity = 10;
    private Policy schedulingPolicy;
    private final Jedis redis;
    private final ObjectMapper objectMapper = Json.getObjectMapper();
    private PostgresUnitOfWork uow = new PostgresUnitOfWork();

    public HallSimulator(Integer id, Policy schedulingPolicy, Jedis redis) {
        this.id = id;
        this.schedulingPolicy = new RandomPolicy(3.0, 5.0);
        this.redis = redis;
    }

    private Client generateClient() {
        List<Privilegy> privileges = uow.getPrivilegyRepository().getAll();

        if (Math.random() < 0.5) { // Adjust the probability as needed, e.g., 0.2 for a 20% chance
            return new Client(
                null,
                UUID.randomUUID().toString().substring(0, 8),
                UUID.randomUUID().toString().substring(0, 8),
                null,
                null
            );
        } else {
            Privilegy randomPrivilege = privileges.get((int) (Math.random() * (privileges.size())));
            return new Client(
                null,
                UUID.randomUUID().toString().substring(0, 8),
                UUID.randomUUID().toString().substring(0, 8),
                randomPrivilege,
                null
            );
        }
    }


    @Override
    public void run() {
        while (true) {
            if(uow.getClientRepository().getAll().size() < maxCapacity){
                var client = this.generateClient();
                var command = new AddClientCommand(client, this.id);
                try {
                    var channel = Settings.getCommandChannel(command.getClass().getSimpleName());
                    this.redis.publish(channel, this.objectMapper.writeValueAsString(command));
                    logger.info(String.format("Sent AddClientCommand to %s channel", channel));
                    Thread.sleep(Math.round(this.schedulingPolicy.getSeconds() * 1000));
                } catch (InterruptedException | JsonProcessingException e) {
                    break;
                }
            }
        }
    }

    public Integer getId() { return this.id; }

    public void setPolicy(Policy schedulingPolicy) { this.schedulingPolicy = schedulingPolicy; }
}
