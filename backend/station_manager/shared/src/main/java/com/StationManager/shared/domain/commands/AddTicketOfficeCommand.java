package com.StationManager.shared.domain.commands;

import com.StationManager.shared.domain.train_station.TicketOffice;

public class AddTicketOfficeCommand extends Command {
    public TicketOffice ticketOffice;
    public Integer hallId;

    public AddTicketOfficeCommand(TicketOffice ticketOffice, Integer hallId) {
        this.ticketOffice = ticketOffice;
        this.hallId = hallId;
    }
}
