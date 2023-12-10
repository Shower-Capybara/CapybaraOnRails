package com.StationManager.app;

import com.StationManager.app.services.MessageBus;
import io.javalin.Javalin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        MessageBus.bootstrap();
        Javalin app = Javalin.create().start(Settings.JAVALIN_PORT);

        app.get(
                "/",
                ctx -> {
                    logger.info("Request finished");
                    ctx.result("Hello World");
                });
    }
}
