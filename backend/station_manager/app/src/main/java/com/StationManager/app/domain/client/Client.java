package com.StationManager.app.domain.client;

import java.awt.*;

public class Client {
    private Integer id;
    private String firstName;
    private String lastName;
    private Short ticketsToBuy;
    private Privilegy privilegy;
    private Point position;

    public Client() {}

    public Client(
            Integer id,
            String firstName,
            String lastName,
            Short ticketsToBuy,
            Privilegy privilegy,
            Point position) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.ticketsToBuy = ticketsToBuy;
        this.privilegy = privilegy;
        this.position = position;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Short getTicketsToBuy() {
        return ticketsToBuy;
    }

    public void setTicketsToBuy(Short ticketsToBuy) {
        this.ticketsToBuy = ticketsToBuy;
    }

    public Privilegy getPrivilegy() {
        return privilegy;
    }

    public void setPrivilegy(Privilegy privilegy) {
        this.privilegy = privilegy;
    }

    public Point getPosition() {
        return position;
    }

    public void setPosition(Point position) {
        this.position = position;
    }
}
