package com.StationManager.app.storage.database.dto;

import com.StationManager.app.domain.client.Client;
import com.StationManager.app.domain.train_station.TicketOffice;

public class ClientPositions {
        private Integer id;
        private Client client;
        private TicketOffice ticketOffice;
        private Integer positionInQueue;

    public ClientPositions() {
        }

        public ClientPositions(Integer id, Client client, TicketOffice ticketOffice, Integer positionInQueue) {
            this.id = id;
            this.client = client;
            this.ticketOffice = ticketOffice;
            this.positionInQueue = positionInQueue;
        }

        public Integer getTicketOfficeId() {
            return ticketOffice.getId();
        }

        public void setTicketOfficeId(Integer id) {
            ticketOffice.setId(id);
        }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public TicketOffice getTicketOffice() {
        return ticketOffice;
    }

    public void setTicketOffice(TicketOffice ticketOffice) {
        this.ticketOffice = ticketOffice;
    }

    public Integer getPositionInQueue() {
        return positionInQueue;
    }

    public void setPositionInQueue(Integer positionInQueue) {
        this.positionInQueue = positionInQueue;
    }

    // Getters and setters
}
