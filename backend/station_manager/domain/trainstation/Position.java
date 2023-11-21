package trainstation;

import java.awt.*;

public class Position {
    private Point start;
    private Point end;

    public Position(Point start, Point end) {
        this.start = start;
        this.end = end;
    }

    public Point getStart() {
        return start;
    }

    public Point getEnd() {
        return end;
    }
}
