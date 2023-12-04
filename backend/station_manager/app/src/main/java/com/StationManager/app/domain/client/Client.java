package com.StationManager.app.domain.client;

import java.awt.*;
import java.util.Objects;

public class Client {
    private Integer id;
    private String firstName;
    private String lastName;
    private Privilegy privilegy;
    private Point position;

    public Client(
        Integer id,
        String firstName,
        String lastName,
        Privilegy privilegy,
        Point position
    ) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.privilegy = privilegy;
        this.position = position;
    }

    public Integer getId() { return id; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public Privilegy getPrivilegy() { return privilegy; }
    public Point getPosition() { return position; }

    public void setId(Integer id) { this.id = id; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public void setPrivilegy(Privilegy privilegy) { this.privilegy = privilegy; }
    public void setPosition(Point position) { this.position = position; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Client client)) return false;
        return Objects.equals(id, client.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, privilegy, position);
    }
}
