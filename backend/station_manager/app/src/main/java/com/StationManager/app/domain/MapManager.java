package com.StationManager.app.domain;

import com.StationManager.app.domain.train_station.Direction;
import com.StationManager.app.domain.train_station.Hall;
import com.StationManager.app.domain.train_station.Segment;
import com.StationManager.app.domain.train_station.TicketOffice;

import java.awt.*;
import java.util.*;
import java.util.function.Function;

public class MapManager {
    /**
     * Returns the closest to client ticket office
     * locations.
     *
     * <p>This method determines the ticket office which is the most suitable for client to be
     * assigned to. If there is one ticketOffice, client is assigned to this ticket office If there
     * are more than one ticket offices, client is assigned to the one, whose next free queue
     * position is closer to client
     *
     * @param point The client point
     * @param ticketOffices The list of ticket offices.
     */
    public static TicketOffice getClosestTicketOffice(
        Point point,
        ArrayList<TicketOffice> ticketOffices
    ) {
        if (ticketOffices.isEmpty()) {
            throw new IllegalStateException("Ticket offices can't be empty");
        }

        return ticketOffices
            .stream()
            .min(
                Comparator.comparingDouble(
                    (ticketOffice) -> point.distance(calculatePositionForNewClient(ticketOffice))
                )
            )
            .get();
    }

    /**
     * Calculates the position for a new client at a ticket office, considering the office's
     * direction and current queue.
     *
     * <p>This method calculates the position for a new client based on the specified ticket
     * office's direction and the current queue of clients. If the queue is empty, it calculates the
     * position near the ticket office, and if the queue is not empty, it calculates the position
     * relative to the last client in the queue.
     *
     * @param ticketOffice The ticket office for which to calculate the new client's position.
     * @return The calculated position for the new client or {@code null} if a suitable position is
     *     not found.
     */
    public static Point calculatePositionForNewClient(TicketOffice ticketOffice) {
        var initialTransformation = new HashMap<Direction, Function<Segment, Point>>() {{
            put(Direction.Up, (segment) -> new Point(segment.start().x + 1, segment.end().y + 1));
            put(Direction.Down, (segment) -> new Point(segment.start().x + 1, segment.start().y - 1));
            put(Direction.Left, (segment) -> new Point(segment.end().x + 1, segment.end().y - 1));
            put(Direction.Right, (segment) -> new Point(segment.start().x - 1, segment.start().y + 1));
        }};

        var initialPoint = initialTransformation
            .get(ticketOffice.getDirection())
            .apply(ticketOffice.getSegment());

        var step = new HashMap<Direction, Point>() {{
            put(Direction.Up, new Point(0, 1));
            put(Direction.Down, new Point(0, -1));
            put(Direction.Left, new Point(1, 0));
            put(Direction.Right, new Point(-1, 0));
        }}.get(ticketOffice.getDirection());

        for (var unused: ticketOffice.getQueue()) initialPoint.translate(step.x, step.y);
        return initialPoint;
    }

    /**
     * Checks if a specified segment is free for use, considering boundaries, entrances, and ticket
     * offices.
     *
     * <p>This method verifies whether a given segment is suitable for use by checking several
     * conditions: 1. It checks if the segment is within the defined bounds. 2. It checks if the
     * segment overlaps with any entrance positions. 3. It checks if the segment overlaps with any
     * ticket office positions. 4. It checks if the segment overlaps with any client positions in
     * the queues of the ticket offices.
     *
     * @param segment The segment to check for availability.
     * @param hall The hall where to check for availability.
     *     queues.
     * @return {@code true} if the segment is free, {@code false} otherwise.
     */
    public static Boolean IsSegmentFree(Segment segment, Hall hall) {
        if (isOutOfBounds(segment, hall.getSegment())) return false;

        if (hall.getEntrances().stream().anyMatch(pos -> segmentsOverlap(pos, segment))) {
            return false;
        }

        if (
            hall.getTicketOffices()
                .stream()
                .anyMatch(ticketOffice -> segmentsOverlap(ticketOffice.getSegment(), segment))
        ) {
            return false;
        }

        return hall.getTicketOffices()
            .stream()
            .noneMatch(
                ticketOffice -> ticketOffice
                    .getQueue()
                    .stream()
                    .anyMatch(
                        client -> segmentContainsPoint(
                            segment,
                            client.getPosition()
                        )
                    )
            );
    }

    /**
     * Checks if a given segment is completely out of bounds based on a specified size.
     *
     * <p>This method determines whether a segment is entirely outside the boundaries defined by a
     * specified size. It compares the coordinates of the segment's start and end points with the
     * start and end points of the size, checking if the segment is outside in any dimension.
     *
     * @param childSegment The segment to check for being out of bounds.
     * @param parentSegment The parent possibly containing the child segment
     * @return {@code true} if the segment is completely out of bounds, {@code false} otherwise.
     */
    private static boolean isOutOfBounds(Segment childSegment, Segment parentSegment) {
        return childSegment.start().x < parentSegment.start().x
            || childSegment.start().y < parentSegment.start().y
            || childSegment.end().x > parentSegment.end().x
            || childSegment.end().y > parentSegment.end().y;
    }

    /**
     * Checks if a given point is within the bounds of the specified segment.
     *
     * <p>This method determines whether a specified point is within the boundaries of a given
     * segment by comparing the point's coordinates with the segment's start and end coordinates.
     *
     * @param segment The segment to check against.
     * @param point The point to check for containment.
     * @return {@code true} if the segment contains the point, {@code false} otherwise.
     */
    public static Boolean segmentContainsPoint(Segment segment, Point point) {
        int pointX = point.x, pointY = point.y;
        int startX = segment.start().x, startY = segment.start().y;
        int endX = segment.end().x, endY = segment.end().y;

        return pointX >= startX && pointX <= endX && pointY >= startY && pointY <= endY;
    }

    /**
     * Checks if two segments have overlapping positions.
     *
     * <p>This method determines if there is any overlap in the positions between two segments. It
     * achieves this by generating sets of points for each segment and checking if there are common
     * points between the two sets.
     *
     * @param segment1 The first segment to check for overlapping positions.
     * @param segment2 The second segment to check for overlapping positions.
     * @return {@code true} if there is an overlap, {@code false} otherwise.
     */
    public static Boolean segmentsOverlap(Segment segment1, Segment segment2) {
        Set<Point> points1 = getAllPoints(segment1);
        Set<Point> points2 = getAllPoints(segment2);

        return points1.stream().anyMatch(points2::contains);
    }

    /**
     * Generates a set of points within the bounds of the given segment.
     *
     * <p>This method creates a set of points representing all possible coordinates within the
     * specified segment, inclusively.
     *
     * @param segment The segment for which to generate the set of points.
     * @return A set of points within the bounds of the segment.
     */
    private static Set<Point> getAllPoints(Segment segment) {
        Set<Point> points = new HashSet<>();

        for (int x = segment.start().x; x <= segment.end().x; x++) {
            for (int y = segment.start().y; y <= segment.end().y; y++) {
                points.add(new Point(x, y));
            }
        }

        return points;
    }
}
