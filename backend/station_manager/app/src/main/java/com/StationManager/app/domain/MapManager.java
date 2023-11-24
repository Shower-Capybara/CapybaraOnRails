package com.StationManager.app.domain;

import com.StationManager.app.domain.client.Client;
import com.StationManager.app.domain.train_station.Direction;
import com.StationManager.app.domain.train_station.Segment;
import com.StationManager.app.domain.train_station.TicketOffice;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MapManager {
    private static Segment size;

    public static void setSize(Segment newSize) {
        size = newSize;
    }

    // Assigning client to the closest suitable ticket office
    public static void assignClientToClosestTicketOffice(
            Client client, ArrayList<Segment> entrances, ArrayList<TicketOffice> ticketOffices) {
        if (ticketOffices.size() == 1) {
            client.setPosition(
                    calculatePositionForNewClient(ticketOffices.get(0), entrances, ticketOffices));
            ticketOffices.get(0).addClient(client);
        } else {
            Map<TicketOffice, Point> points = new HashMap<>();

            for (TicketOffice t : ticketOffices) {
                points.put(t, calculatePositionForNewClient(t, entrances, ticketOffices));
            }

            TicketOffice closestTicketOffice = null;
            Point closestPoint = null;

            for (Map.Entry<TicketOffice, Point> entry : points.entrySet()) {
                Point currentPoint = entry.getValue();

                if (closestPoint == null
                        || currentPoint.distance(client.getPosition())
                                < closestPoint.distance(client.getPosition())) {
                    closestPoint = currentPoint;
                    closestTicketOffice = entry.getKey();
                }
            }

            client.setPosition(
                    calculatePositionForNewClient(closestTicketOffice, entrances, ticketOffices));
            closestTicketOffice.addClient(client);
        }
    }

    // Calculation of Point (Position) for new client
    public static Point calculatePositionForNewClient(
            TicketOffice ticketOffice,
            ArrayList<Segment> entrances,
            ArrayList<TicketOffice> ticketOffices) {
        Point calculatedPoint = null;

        if (ticketOffice.getQueue().isEmpty()) {
            // Calculation of first client's position in queue

            // TicketBox Is In Top
            if (ticketOffice.getDirection() == Direction.Up) {
                int midX = ticketOffice.getPosition().getStart().x + 1;
                int midY = ticketOffice.getPosition().getEnd().y;
                calculatedPoint = new Point(midX, midY + 1);
            }

            // TicketBox Is In Bottom
            if (ticketOffice.getDirection() == Direction.Down) {
                int midX = ticketOffice.getPosition().getStart().x + 1;
                int midY = ticketOffice.getPosition().getStart().y;
                calculatedPoint = new Point(midX, midY - 1);
            }

            // TicketBox is to the right
            if (ticketOffice.getDirection() == Direction.Right) {
                int midX = ticketOffice.getPosition().getStart().x;
                int midY = ticketOffice.getPosition().getStart().y + 1;
                calculatedPoint = new Point(midX - 1, midY);
            }

            // TicketBox is to the left
            if (ticketOffice.getDirection() == Direction.Left) {
                int midX = ticketOffice.getPosition().getEnd().x;
                int midY = ticketOffice.getPosition().getStart().y + 1;
                calculatedPoint = new Point(midX + 1, midY);
            }

            if (IsFree(new Segment(calculatedPoint, calculatedPoint), entrances, ticketOffices)) {
                return calculatedPoint;
            }
        } else {
            // Calculation of further clients' positions in queue

            Point lastClientPoint = ticketOffice.getQueue().getLast().getPosition();
            // TicketBox Is In Top
            if (ticketOffice.getDirection() == Direction.Up) {
                int newX = lastClientPoint.x;
                int newY = lastClientPoint.y + 1;
                calculatedPoint = new Point(newX, newY);
            }

            // TicketBox Is In Bottom
            if (ticketOffice.getDirection() == Direction.Down) {
                int newX = lastClientPoint.x;
                int newY = lastClientPoint.y - 1;
                calculatedPoint = new Point(newX, newY);
            }

            // TicketBox Is In the Right
            if (ticketOffice.getDirection() == Direction.Right) {
                int newX = lastClientPoint.x - 1;
                int newY = lastClientPoint.y;
                calculatedPoint = new Point(newX, newY);
            }

            if (ticketOffice.getDirection() == Direction.Left) {
                int newX = lastClientPoint.x + 1;
                int newY = lastClientPoint.y;
                calculatedPoint = new Point(newX, newY);
            }

            if (IsFree(new Segment(calculatedPoint, calculatedPoint), entrances, ticketOffices)) {
                return calculatedPoint;
            }
        }

        return calculatedPoint;
    }

    // Check if position is free
    public static Boolean IsFree(
            Segment segment, ArrayList<Segment> entrances, ArrayList<TicketOffice> ticketOffices) {
        // Check if position is not out of bounds
        if (segment.getStart().x < size.getStart().x
                || segment.getStart().y < size.getStart().y
                || segment.getEnd().x > size.getEnd().x
                || segment.getEnd().y > size.getEnd().y) {
            return false;
        }

        // Check if position is free
        if (entrances.stream().anyMatch(pos -> posiotionsOverlap(pos, segment))) {
            return false;
        }
        if (ticketOffices.stream()
                .anyMatch(ticketOffice -> posiotionsOverlap(ticketOffice.getPosition(), segment))) {
            return false;
        }
        if (ticketOffices.stream()
                .anyMatch(
                        ticketOffice ->
                                ticketOffice.getQueue().stream()
                                        .anyMatch(
                                                client ->
                                                        positionContainsPoint(
                                                                segment, client.getPosition())))) {
            return false;
        }
        return true;
    }

    // Check if position contains point
    public static Boolean positionContainsPoint(Segment segment, Point point) {
        int pointX = point.x;
        int pointY = point.y;

        int startX = segment.getStart().x;
        int startY = segment.getStart().y;
        int endX = segment.getEnd().x;
        int endY = segment.getEnd().y;

        return pointX >= startX && pointX <= endX && pointY >= startY && pointY <= endY;
    }

    // Check if two postiotions overlap each other
    public static Boolean posiotionsOverlap(Segment segment1, Segment segment2) {
        ArrayList<Point> points1 = new ArrayList<>();
        ArrayList<Point> points2 = new ArrayList<>();

        for (int x = segment1.getStart().x; x <= segment1.getEnd().x; x++) {
            for (int y = segment1.getStart().y; y <= segment1.getEnd().y; y++) {
                points1.add(new Point(x, y));
            }
        }

        for (int x = segment2.getStart().x; x <= segment2.getEnd().x; x++) {
            for (int y = segment2.getStart().y; y <= segment2.getEnd().y; y++) {
                points2.add(new Point(x, y));
            }
        }

        return points1.stream()
                .anyMatch(point1 -> points2.stream().anyMatch(point2 -> point1.equals(point2)));
    }
}
