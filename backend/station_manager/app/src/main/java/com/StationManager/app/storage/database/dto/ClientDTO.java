package com.StationManager.app.storage.database.dto;

import com.StationManager.app.storage.database.converters.PointConverter;
import jakarta.persistence.*;
//import org.locationtech.jts.geom.Point;
import java.awt.Point;


@Entity
@Table(name = "clients",
       uniqueConstraints = {
            @UniqueConstraint(columnNames = "id"),
       })
public class ClientDTO {
    @Id
    @Column(name = "id", nullable = false, unique = true)
    private int id;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Transient
    private String privilegy;
    @Column(name = "position")
    @Convert(converter = PointConverter.class)
    private Point position;

    public ClientDTO() {}

    public ClientDTO(int id, String firstName, String lastName, String privilegy, Point position) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.privilegy = privilegy;
        this.position = position;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public String getPrivilegy() {
        return privilegy;
    }

    public void setPrivilegy(String privilegy) {
        this.privilegy = privilegy;
    }

    public Point getPosition() {
        return position;
    }

    public void setPosition(Point position) {
        this.position = position;
    }

}
