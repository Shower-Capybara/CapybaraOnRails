package com.StationManager.shared.storage.database.dto;


public class HallsTicketOffices {
    private Integer id;
    private HallDTO hall;
    private TicketOfficeDTO ticketOffice;

    public HallsTicketOffices() {

    }

    public HallsTicketOffices(HallDTO hall, TicketOfficeDTO ticketOffice) {
        this.hall = hall;
        this.ticketOffice = ticketOffice;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public HallDTO getHall() {
        return hall;
    }

    public void setHall(HallDTO hall) {
        this.hall = hall;
    }

    public TicketOfficeDTO getTicketOffice() {
        return ticketOffice;
    }

    public void setTicketOffice(TicketOfficeDTO ticketOffice) {
        this.ticketOffice = ticketOffice;
    }
}
