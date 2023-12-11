package com.StationManager.shared.storage.database.dto;


import com.StationManager.shared.domain.client.Client;

public class ClientPositions {
    private Integer id;
    private Client client;
    private TicketOfficeDTO ticketOffice;
    private Integer positionInQueue;

    public ClientPositions() {
    }

    public ClientPositions(Client client, TicketOfficeDTO ticketOffice, Integer positionInQueue) {
        this.client = client;
        this.ticketOffice = ticketOffice;
        this.positionInQueue = positionInQueue;
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

    public TicketOfficeDTO getTicketOffice() {
        return ticketOffice;
    }

    public void setTicketOffice(TicketOfficeDTO ticketOffice) {
        this.ticketOffice = ticketOffice;
    }

    public Integer getPositionInQueue() {
        return positionInQueue;
    }

    public void setPositionInQueue(Integer positionInQueue) {
        this.positionInQueue = positionInQueue;
    }

}
