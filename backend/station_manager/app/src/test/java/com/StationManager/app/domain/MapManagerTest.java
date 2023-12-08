package com.StationManager.app.domain;

import com.StationManager.app.domain.client.Client;
import com.StationManager.app.domain.client.Privilegy;
import com.StationManager.app.domain.train_station.Direction;
import com.StationManager.app.domain.train_station.Hall;
import com.StationManager.app.domain.train_station.Segment;
import com.StationManager.app.domain.train_station.TicketOffice;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class MapManagerTest {
    static Hall getHall(){
        List<Segment> entrances = List.of(new Segment(new Point(0, 5), new Point(0, 7)));
        ArrayList<TicketOffice> ticketOffices = new ArrayList<>();
        return new Hall(
            1,
            new Segment(new Point(0, 0), new Point(20, 20)),
            entrances,
            ticketOffices
        );
    }

    static ArrayList<TicketOffice> getTicketOffices(){
        Segment ticketOfficeSegment1 = new Segment(new Point(0, 0), new Point(2, 1));
        Segment ticketOfficeSegment2 = new Segment(new Point(4, 18), new Point(6, 19));
        Segment ticketOfficeSegment3 = new Segment(new Point(0, 5), new Point(1, 7));
        Segment ticketOfficeSegment4 = new Segment(new Point(18, 4), new Point(19, 6));

        int timeToServeTicket = 5;

        TicketOffice ticketOffice1 = new TicketOffice(
            1,
            ticketOfficeSegment1,
            Direction.Up,
            timeToServeTicket
        );
        TicketOffice ticketOffice2 = new TicketOffice(
            2,
            ticketOfficeSegment2,
            Direction.Down,
            timeToServeTicket
        );
        TicketOffice ticketOffice3 = new TicketOffice(
            3,
            ticketOfficeSegment3,
            Direction.Left,
            timeToServeTicket
        );
        TicketOffice ticketOffice4 = new TicketOffice(
            4,
            ticketOfficeSegment4,
            Direction.Right,
            timeToServeTicket
        );
        return new ArrayList<>(List.of(ticketOffice1, ticketOffice2, ticketOffice3, ticketOffice4));
    }

    static Client getClient(int id, Point position) {
        return new Client(id, "Fname", "Sname", new Privilegy("ordinary", 0), position);
    }

    @Test
    @DisplayName("Creating three different segments, two of which overlap and two doesn't, and " +
        "checking if they overlap")
    void testSegmentsOverlap(){
        Segment segment1 = new Segment(new Point(0, 0), new Point(2, 1));
        assertFalse(MapManager.segmentsOverlap(
            segment1,
            new Segment(new Point(0, 2), new Point(2, 3))
        ));
        assertTrue(MapManager.segmentsOverlap(
            segment1,
            new Segment(new Point(0, 1), new Point(2, 2))
        ));
    }

    @Test
    @DisplayName("Creating segment and a few points, then checks if segment contains the points")
    void testSegmentContainsPoint(){
        Segment segment1 = new Segment(new Point(0, 0), new Point(2, 1));
        assertTrue(MapManager.segmentContainsPoint(segment1, new Point(1, 1)));
        assertTrue(MapManager.segmentContainsPoint(segment1, new Point(2, 1)));
        assertFalse(MapManager.segmentContainsPoint(segment1, new Point(3, 1)));
        assertFalse(MapManager.segmentContainsPoint(segment1, new Point(2, 2)));
    }

    @Test
    @DisplayName("Creating ticket office, adding it to hall, than checking if segment in hall " +
        "of created ticket office and two more segments is free")
    void testIsSegmentFree() {
        Hall hall = getHall();
        Segment ticketOfficeSegment1 = new Segment(new Point(0, 0), new Point(2, 1));
        TicketOffice ticketOffice1 = new TicketOffice(
            1,
            ticketOfficeSegment1,
            Direction.Up,
            5
        );
        hall.addTicketOffice(ticketOffice1);
        assertFalse(MapManager.IsSegmentFree(ticketOfficeSegment1, hall));
        assertFalse(MapManager.IsSegmentFree(new Segment(new Point(1, 1), new Point(3, 2)), hall));
        assertTrue(MapManager.IsSegmentFree(new Segment(new Point(3, 0), new Point(5, 1)), hall));
    }

    @ParameterizedTest
    @MethodSource("closestTicketOfficeTestData")
    @DisplayName("Creating a few different points and ticket offices, then testing if calculation " +
        "of the closest ticket office to given point is correct")
    void testGetClosestTicketOffice(Client client,
                                    ArrayList<TicketOffice> ticketOffices,
                                    TicketOffice expectedTicketOffice){
        assertEquals(expectedTicketOffice, MapManager.getClosestTicketOffice(client, ticketOffices));
    }

    private static Stream<Arguments> closestTicketOfficeTestData(){
        return Stream.of(
            Arguments.of(getClient(1, new Point(5, 2)), getTicketOffices(), getTicketOffices().get(0)),
            Arguments.of(getClient(2, new Point(3, 15)), getTicketOffices(), getTicketOffices().get(1)),
            Arguments.of(getClient(3, new Point(6, 8)), getTicketOffices(), getTicketOffices().get(2)),
            Arguments.of(getClient(4, new Point(13, 12)), getTicketOffices(), getTicketOffices().get(3))
        );
    }

    @ParameterizedTest
    @MethodSource("newClientPositionTestData")
    @DisplayName("Creating a few empty ticket Offices and checking if calculation of position" +
        "client should take is correct")
    void testCalculatePositionForNewClient(TicketOffice ticketOffice, Point expectedPoint){
        assertEquals(expectedPoint, MapManager.calculatePositionForNewClient(ticketOffice, getClient(11, new Point(15, 8))));
    }

    private static Stream<Arguments> newClientPositionTestData(){
        return Stream.of(
            Arguments.of(getTicketOffices().get(0), new Point(1, 2)),
            Arguments.of(getTicketOffices().get(1), new Point(5, 17)),
            Arguments.of(getTicketOffices().get(2), new Point(2, 6)),
            Arguments.of(getTicketOffices().get(3), new Point(17, 5))
        );
    }

    @ParameterizedTest
    @MethodSource("newClientPositionInNotEmptyQueueTestData")
    @DisplayName("Creating a few ticket Offices with some queue and checking if calculation of" +
        " position client should take is correct")
    void testCalculatePositionForNewClientInNotEmptyQueue(TicketOffice ticketOffice, Point expectedPoint){
        assertEquals(
            expectedPoint,
            MapManager.calculatePositionForNewClient(ticketOffice, getClient(11, new Point(15, 8)))
        );
    }

    private static Stream<Arguments> newClientPositionInNotEmptyQueueTestData(){
        var ticketOfficeUp = getTicketOffices().get(0);
        var ticketOfficeDown = getTicketOffices().get(1);
        var ticketOfficeLeft = getTicketOffices().get(2);
        var ticketOfficeRight = getTicketOffices().get(3);

        ticketOfficeUp.addClient(getClient(1, new Point(1, 2)));

        ticketOfficeDown.addClient(getClient(2, new Point(5, 17)));
        ticketOfficeDown.addClient(getClient(3, new Point(5, 16)));

        ticketOfficeLeft.addClient(getClient(4, new Point(2, 6)));
        ticketOfficeLeft.addClient(getClient(5, new Point(3, 6)));
        ticketOfficeLeft.addClient(getClient(6, new Point(4, 6)));

        ticketOfficeRight.addClient(getClient(7, new Point(17, 5)));
        ticketOfficeRight.addClient(getClient(8, new Point(16, 5)));
        ticketOfficeRight.addClient(getClient(9, new Point(15, 5)));
        ticketOfficeRight.addClient(getClient(10, new Point(14, 5)));

        return Stream.of(
            Arguments.of(ticketOfficeUp, new Point(1, 3)),
            Arguments.of(ticketOfficeDown, new Point(5, 15)),
            Arguments.of(ticketOfficeLeft, new Point(5, 6)),
            Arguments.of(ticketOfficeRight, new Point(13, 5))
        );
    }
}