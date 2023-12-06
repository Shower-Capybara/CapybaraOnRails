package com.StationManager.app.domain.train_station;

import com.StationManager.app.domain.MapManager;

import java.awt.Point;
import java.util.HashSet;
import java.util.Set;

public class Segment {
    private Point Start;
    private Point End;

    public Segment(Point start, Point end) {
        this.Start = start;
        this.End = end;
    }

    public Point start() {
        return Start;
    }

    public Point end() {
        return End;
    }

    public Set<Point> getAllPoints() {
        Set<Point> points = new HashSet<>();

        for (int x = this.start().x; x <= this.end().x; x++) {
            for (int y = this.start().y; y <= this.end().y; y++) {
                points.add(new Point(x, y));
            }
        }

        return points;
    }
}
