package com.StationManager.generator.simulator;

import com.StationManager.generator.Main;
import com.StationManager.generator.Settings;
import com.StationManager.generator.simulator.models.payload.ClientBeingServedEventPayload;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class TicketOfficeSimulator implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    private final Integer id;
    private final BlockingQueue<String> messageQueue = new LinkedBlockingQueue<>();
    private final Jedis redis;
    private final Gson gson = new Gson();

    public TicketOfficeSimulator(Integer id, Jedis redis) {
        this.id = id;
        this.redis = redis;

    }

    public void publishMessage(String message) {
        this.messageQueue.add(message);
    }

    private JsonObject getJsonObject(String data) {
        JsonElement je = gson.fromJson(data, JsonElement.class);
        return je.getAsJsonObject();
    }

    private void handleClientBeingServedEvent(JsonObject data) {
        var payload = gson.fromJson(data, ClientBeingServedEventPayload.class);
//        for (int i = 0; i < payload.client.numTickets; i++) {
//            try {
//                Thread.sleep(1000); // replace the default value
//            } catch (InterruptedException e) {
//                break;
//            }

//        }

    }

    @Override
    public void run() {
        while (true) {
            try {
                var message = this.messageQueue.take();
                var obj = getJsonObject(message);
                String type = obj.get("type").getAsString();
//                gson.fromJson();
//                String region = location.get("region").getAsString();




//                var event = gson.fromJson(String.valueOf(message.get));
                redis.publish(Settings.REDIS_CHANNEL, "1"); // publish new client message
                logger.info("Publishing new client command");
            } catch (InterruptedException e) {
                break;
            }
        }
    }

    public Integer getId() {return this.id; }
}
