package com.StationManager.shared.domain.events;

import com.StationManager.shared.domain.train_station.TicketOffice;

public class TicketOfficeAddedEvent extends Event {
    public TicketOffice ticketOffice;

    public TicketOfficeAddedEvent(TicketOffice ticketOffice) {
        super();
        this.ticketOffice = ticketOffice;
    }
}
