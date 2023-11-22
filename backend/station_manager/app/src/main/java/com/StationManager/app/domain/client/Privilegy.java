package com.StationManager.app.domain.client;

import java.util.Objects;

public class Privilegy {
    private String type;
    private Integer priority;
    private Integer significance;

    public Privilegy(String type, Integer priority, Integer significance) {
        this.type = type;
        this.priority = priority;
        this.significance = significance;
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

    public Integer getSignificance() {
        return significance;
    }

    public void setSignificance(Integer significance) {
        this.significance = significance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Privilegy privilegy)) return false;
        return Objects.equals(type, privilegy.type)
                && Objects.equals(priority, privilegy.priority)
                && Objects.equals(significance, privilegy.significance);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, priority, significance);
    }
}
