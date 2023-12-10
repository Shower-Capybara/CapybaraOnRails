package com.StationManager.simulator;

public class Settings {
    public static String REDIS_HOST = System.getenv("REDIS_HOST");
    public static Integer REDIS_PORT = Integer.parseInt(System.getenv("REDIS_PORT"));
    public static String REDIS_EVENTS_CHANNEL = System.getenv("REDIS_EVENTS_CHANNEL");
    public static String REDIS_COMMANDS_CHANNEL = System.getenv("REDIS_COMMANDS_CHANNEL");
}