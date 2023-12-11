package com.StationManager.app;

public class Settings {
    public static int JAVALIN_PORT = Integer.parseInt(System.getenv("JAVALIN_PORT"));
    public static String REDIS_HOST = System.getenv("REDIS_HOST");
    public static Integer REDIS_PORT = Integer.parseInt(System.getenv("REDIS_PORT"));
    public static String REDIS_EVENTS_CHANNEL_PREFIX = System.getenv("REDIS_EVENTS_CHANNEL_PREFIX");
    public static String REDIS_COMMANDS_CHANNEL_PREFIX = System.getenv("REDIS_COMMANDS_CHANNEL_PREFIX");

    public static String getEventChannel(String eventName) {
        return String.format("%s:%s", Settings.REDIS_EVENTS_CHANNEL_PREFIX, eventName);
    }

    public static String getCommandChannel(String eventName) {
        return String.format("%s:%s", Settings.REDIS_COMMANDS_CHANNEL_PREFIX, eventName);
    }
}
