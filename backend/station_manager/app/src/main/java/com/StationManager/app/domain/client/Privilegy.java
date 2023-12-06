package com.StationManager.app.domain.client;

import java.util.Objects;

public class Privilegy {
    private String type;
    private Integer significance;

    public Privilegy(String type, Integer significance) {
        this.type = type;
        this.significance = significance;
    }

    public String getType() { return type; }
    public Integer getSignificance() { return significance; }

    public void setType(String type) { this.type = type; }
    public void setSignificance(Integer significance) { this.significance = significance; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Privilegy privilegy)) return false;
        return Objects.equals(type, privilegy.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, significance);
    }
}
