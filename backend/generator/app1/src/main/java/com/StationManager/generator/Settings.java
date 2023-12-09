package com.StationManager.generator;

public class Settings {
    public static String REDIS_HOST = System.getenv("REDIS_HOST");
    public static Integer REDIS_PORT = Integer.parseInt(System.getenv("REDIS_PORT"));
    public static String REDIS_CHANNEL = System.getenv("REDIS_CHANNEL");
}
