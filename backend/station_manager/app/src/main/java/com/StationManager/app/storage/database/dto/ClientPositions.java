package com.StationManager.app.storage.database.dto;

import com.StationManager.app.domain.client.Client;
import com.StationManager.app.domain.train_station.TicketOffice;
import jakarta.persistence.*;

//@Entity
//@Table(name = "client_positions")
public class ClientPositions {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "id")
    private Integer id;
//    @ManyToOne
//    @JoinColumn(name = "client_id")
    private Client client;
//    @ManyToOne
//    @JoinColumn(name = "ticket_office_id")
    private TicketOfficeDTO ticketOffice;
//    @Column(name = "position_in_queue")
    private Integer positionInQueue;

    public ClientPositions() {
        }

        public ClientPositions(Client client, TicketOfficeDTO ticketOffice, Integer positionInQueue) {
            this.client = client;
            this.ticketOffice = ticketOffice;
            this.positionInQueue = positionInQueue;
        }

//        public Integer getTicketOfficeId() {
//            return ticketOffice.getId();
//        }
//
//        public void setTicketOfficeId(Integer id) {
//            ticketOffice.setId(id);
//        }

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

    // Getters and setters
}
