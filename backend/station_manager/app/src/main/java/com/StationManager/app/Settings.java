package com.StationManager.app;

public class Settings {
    public static int JAVALIN_PORT = Integer.parseInt(System.getenv("JAVALIN_PORT"));
    public static String PG_URL = System.getenv("PG_URL");
    public static String PG_USER = System.getenv("PG_USER");
    public static String PG_PASSWORD = System.getenv("PG_PASSWORD");
}
