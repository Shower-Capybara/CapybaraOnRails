package com.StationManager.simulator;

public class Settings {
    public static String REDIS_HOST = System.getenv("REDIS_HOST").trim();
    public static Integer REDIS_PORT = Integer.parseInt(System.getenv("REDIS_PORT").trim());
    public static String REDIS_EVENTS_CHANNEL_PREFIX = System.getenv("REDIS_EVENTS_CHANNEL_PREFIX").trim();
    public static String REDIS_COMMANDS_CHANNEL_PREFIX = System.getenv("REDIS_COMMANDS_CHANNEL_PREFIX").trim();

    public static String getEventChannel(String eventName) {
        return String.format("%s:%s", Settings.REDIS_EVENTS_CHANNEL_PREFIX, eventName);
    }

    public static String getCommandChannel(String eventName) {
        return String.format("%s:%s", Settings.REDIS_COMMANDS_CHANNEL_PREFIX, eventName);
    }
}
