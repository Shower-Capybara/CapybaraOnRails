package com.StationManager.app.domain.commands;

import com.StationManager.app.domain.train_station.TicketOffice;

public class AddTicketOfficeCommand extends Command {
    public TicketOffice ticketOffice;
    public Integer hallId;

    public AddTicketOfficeCommand(TicketOffice ticketOffice, Integer hallId) {
        this.ticketOffice = ticketOffice;
        this.hallId = hallId;
    }
}
