package com.StationManager.app.services.handlers.events;

import com.StationManager.app.services.unitofwork.UnitOfWork;
import com.StationManager.shared.domain.events.LogRecordEvent;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class LogRecordEventHandler implements EventHandler<LogRecordEvent> {
    @Override
    public void handle(LogRecordEvent logRecordEvent, UnitOfWork uow) {
        var client = logRecordEvent.client;
        var startTime = logRecordEvent.startTime;
        var endTime = logRecordEvent.endTime;
        String filePath = "log.txt";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            writer.write("ClientId: " + client.getId() + ", StartTime: " + startTime + ", EndTime: " + endTime);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
