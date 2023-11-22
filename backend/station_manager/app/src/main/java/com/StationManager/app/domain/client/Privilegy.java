package com.StationManager.app.domain.client;

import java.util.Objects;

public class Privilegy {
    private String type;
    private Integer priority;

    public Privilegy(String type, Integer priority) {
        this.type = type;
        this.priority = priority;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Privilegy privilegy = (Privilegy) o;
        return Objects.equals(type, privilegy.type) && Objects.equals(priority, privilegy.priority);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, priority);
    }
}
