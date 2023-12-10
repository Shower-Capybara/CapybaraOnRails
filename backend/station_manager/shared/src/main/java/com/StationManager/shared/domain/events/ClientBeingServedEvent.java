package com.StationManager.shared.domain.events;

import com.StationManager.shared.domain.client.Client;
import com.StationManager.shared.domain.train_station.TicketOffice;

public class ClientBeingServedEvent extends Event {
    public TicketOffice ticketOffice;
    public Client client;

    public ClientBeingServedEvent(TicketOffice ticketOffice, Client client) {
        this.ticketOffice = ticketOffice;
        this.client = client;
    }

}
