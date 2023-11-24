package com.StationManager.app.domain;

import com.StationManager.app.domain.client.Client;
import com.StationManager.app.domain.train_station.Direction;
import com.StationManager.app.domain.train_station.Hall;
import com.StationManager.app.domain.train_station.Position;
import com.StationManager.app.domain.train_station.TicketOffice;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MapManager {
    private Position size;
    private Hall hall;

    public MapManager(Position size, Hall hall) {
        this.size = size;
        this.hall = hall;
    }

    // Assigning client to the closest suitable ticket office
    public void assignClientToClosestTicketOffice(
            Client client, ArrayList<TicketOffice> ticketOffices) {
        if (ticketOffices.size() == 1) {
            client.setPosition(calculatePositionForNewClient(ticketOffices.get(0)));
            ticketOffices.get(0).addClient(client);
        } else {
            Map<TicketOffice, Point> points = new HashMap<>();

            for (TicketOffice t : ticketOffices) {
                points.put(t, calculatePositionForNewClient(t));
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

            client.setPosition(calculatePositionForNewClient(closestTicketOffice));
            closestTicketOffice.addClient(client);
        }
    }

    // Calculation of Point (Position) for new client
    public Point calculatePositionForNewClient(TicketOffice ticketOffice) {
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

            if (positionIsFree(new Position(calculatedPoint, calculatedPoint))) {
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

            if (positionIsFree(new Position(calculatedPoint, calculatedPoint))) {
                return calculatedPoint;
            }
        }

        return calculatedPoint;
    }

    // Check if position is free
    public Boolean positionIsFree(Position position) {
        // Check if position is not out of bounds
        if (position.getStart().x < size.getStart().x
                || position.getStart().y < size.getStart().y
                || position.getEnd().x > size.getEnd().x
                || position.getEnd().y > size.getEnd().y) {
            return false;
        }

        var entrances = hall.getEntrances();
        var ticketOffices = hall.getTicketOffices();

        // Check if position is free
        if (entrances.stream().anyMatch(pos -> posiotionsOverlap(pos, position))) {
            return false;
        }
        if (ticketOffices.stream()
                .anyMatch(
                        ticketOffice -> posiotionsOverlap(ticketOffice.getPosition(), position))) {
            return false;
        }
        if (ticketOffices.stream()
                .anyMatch(
                        ticketOffice ->
                                ticketOffice.getQueue().stream()
                                        .anyMatch(
                                                client ->
                                                        positionContainsPoint(
                                                                position, client.getPosition())))) {
            return false;
        }
        return true;
    }

    // Check if position contains point
    public static Boolean positionContainsPoint(Position position, Point point) {
        int pointX = point.x;
        int pointY = point.y;

        int startX = position.getStart().x;
        int startY = position.getStart().y;
        int endX = position.getEnd().x;
        int endY = position.getEnd().y;

        return pointX >= startX && pointX <= endX && pointY >= startY && pointY <= endY;
    }

    // Check if two postiotions overlap each other
    public static Boolean posiotionsOverlap(Position position1, Position position2) {
        ArrayList<Point> points1 = new ArrayList<>();
        ArrayList<Point> points2 = new ArrayList<>();

        for (int x = position1.getStart().x; x <= position1.getEnd().x; x++) {
            for (int y = position1.getStart().y; y <= position1.getEnd().y; y++) {
                points1.add(new Point(x, y));
            }
        }

        for (int x = position2.getStart().x; x <= position2.getEnd().x; x++) {
            for (int y = position2.getStart().y; y <= position2.getEnd().y; y++) {
                points2.add(new Point(x, y));
            }
        }

        return points1.stream()
                .anyMatch(point1 -> points2.stream().anyMatch(point2 -> point1.equals(point2)));
    }
}
