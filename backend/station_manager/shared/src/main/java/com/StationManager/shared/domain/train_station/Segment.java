package com.StationManager.shared.domain.train_station;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.awt.*;
import java.util.HashSet;
import java.util.Set;

public class Segment {
    public Point start;
    public Point end;

    private Segment() {} // required for jackson

    public Segment(Point start, Point end) {
        this.start = start;
        this.end = end;
    }

    @JsonIgnore
    public Set<Point> getAllPoints() {
        Set<Point> points = new HashSet<>();

        for (int x = this.start.x; x <= this.end.x; x++) {
            for (int y = this.start.y; y <= this.end.y; y++) {
                points.add(new Point(x, y));
            }
        }

        return points;
    }
}
