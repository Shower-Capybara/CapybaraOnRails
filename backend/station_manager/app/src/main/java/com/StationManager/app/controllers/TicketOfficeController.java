package com.StationManager.app.controllers;

import io.javalin.http.Context;

public class TicketOfficeController {
    public static void createTicketOffice(Context ctx) {
        // Implementation
        ctx.status(200).result("Ticket office created successfully");
    }

    public static void closeTicketOffice(Context ctx) {
        // Implementation
        ctx.status(200).result("Ticket office closed successfully");
    }

    public static void openTicketOffice(Context ctx) {
        // Implementation
        ctx.status(200).result("Ticket office opened successfully");
    }
}
