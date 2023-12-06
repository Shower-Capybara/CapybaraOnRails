package com.StationManager.app.domain.events;

import com.StationManager.app.domain.train_station.TicketOffice;

public class TicketOfficeAddedEvent extends Event {
    public TicketOffice ticketOffice;

    public TicketOfficeAddedEvent(TicketOffice ticketOffice) {
        super();
        this.ticketOffice = ticketOffice;
    }
}
