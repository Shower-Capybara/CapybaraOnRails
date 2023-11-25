package com.StationManager.app.domain;

import com.StationManager.app.domain.train_station.Direction;
import com.StationManager.app.domain.train_station.Hall;
import com.StationManager.app.domain.train_station.Segment;
import com.StationManager.app.domain.train_station.TicketOffice;
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
        ArrayList<Segment> entrances = new ArrayList<>();
        ArrayList<TicketOffice> ticketOffices = new ArrayList<>();
        return new Hall(
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
            ticketOfficeSegment1,
            Direction.Up,
            timeToServeTicket
        );
        TicketOffice ticketOffice2 = new TicketOffice(
            ticketOfficeSegment2,
            Direction.Down,
            timeToServeTicket
        );
        TicketOffice ticketOffice3 = new TicketOffice(
            ticketOfficeSegment3,
            Direction.Left,
            timeToServeTicket
        );
        TicketOffice ticketOffice4 = new TicketOffice(
            ticketOfficeSegment4,
            Direction.Right,
            timeToServeTicket
        );
        return new ArrayList<>(List.of(ticketOffice1, ticketOffice2, ticketOffice3, ticketOffice4));
    }

    @Test
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
    void testSegmentContainsPoint(){
        Segment segment1 = new Segment(new Point(0, 0), new Point(2, 1));
        assertTrue(MapManager.segmentContainsPoint(segment1, new Point(1, 1)));
        assertTrue(MapManager.segmentContainsPoint(segment1, new Point(2, 1)));
        assertFalse(MapManager.segmentContainsPoint(segment1, new Point(3, 1)));
        assertFalse(MapManager.segmentContainsPoint(segment1, new Point(2, 2)));
    }

    @Test
    void testIsSegmentFree() {
        Hall hall = getHall();
        Segment ticketOfficeSegment1 = new Segment(new Point(0, 0), new Point(2, 1));
        TicketOffice ticketOffice1 = new TicketOffice(
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
    void testGetClosestTicketOffice(Point point,
                                    ArrayList<TicketOffice> ticketOffices,
                                    TicketOffice expectedTicketOffice){
        assertEquals(expectedTicketOffice, MapManager.getClosestTicketOffice(point, ticketOffices));
    }

    private static Stream<Arguments> closestTicketOfficeTestData(){
        return Stream.of(
            Arguments.of(new Point(5, 2), getTicketOffices(), getTicketOffices().get(0)),
            Arguments.of(new Point(3, 15), getTicketOffices(), getTicketOffices().get(1)),
            Arguments.of(new Point(6, 8), getTicketOffices(), getTicketOffices().get(2)),
            Arguments.of(new Point(13, 12), getTicketOffices(), getTicketOffices().get(3))
        );
    }

    @ParameterizedTest
    @MethodSource("newClientPositionTestData")
    void testCalculatePositionForNewClient(TicketOffice ticketOffice, Point expectedPoint){
        assertEquals(expectedPoint, MapManager.calculatePositionForNewClient(ticketOffice));
    }

    private static Stream<Arguments> newClientPositionTestData(){
        return Stream.of(
            Arguments.of(getTicketOffices().get(0), new Point(1, 2)),
            Arguments.of(getTicketOffices().get(1), new Point(5, 17)),
            Arguments.of(getTicketOffices().get(2), new Point(2, 6)),
            Arguments.of(getTicketOffices().get(3), new Point(17, 5))
        );
    }
}