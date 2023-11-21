package com.StationManager.app.domain;

import com.StationManager.app.domain.client.Client;
import com.StationManager.app.domain.trainstation.TicketOffice;

import java.util.ArrayList;

public class MapManager {
    private Integer height;
    private Integer width;
    private ArrayList<Client> clients;
    private ArrayList<TicketOffice> ticketOffices;

    public MapManager(Integer width, Integer height) {
        this.width = width;
        this.height = height;
        this.clients = new ArrayList<>();
        this.ticketOffices = new ArrayList<>();
    }

    public void FindClosestTicketOffice(Client client) {}
}
