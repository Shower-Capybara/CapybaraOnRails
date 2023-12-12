package com.StationManager.shared.storage.database.converters;

import com.StationManager.shared.domain.train_station.Segment;
import jakarta.persistence.AttributeConverter;

import java.awt.*;

public class SegmentConverter implements AttributeConverter<Segment, org.locationtech.jts.geom.MultiPoint> {
    @Override
    public org.locationtech.jts.geom.MultiPoint convertToDatabaseColumn(Segment segment) {
        var geometryFactory = new org.locationtech.jts.geom.GeometryFactory();
        var coordinates = new org.locationtech.jts.geom.Coordinate[]{
            new org.locationtech.jts.geom.Coordinate(segment.start.x, segment.start.y),
            new org.locationtech.jts.geom.Coordinate(segment.end.x, segment.end.y)
        };
        return geometryFactory.createMultiPointFromCoords(coordinates);
    }

    @Override
    public Segment convertToEntityAttribute(org.locationtech.jts.geom.MultiPoint multiPoint) {
        var coordinates = multiPoint.getCoordinates();
        var start = new Point((int) coordinates[0].getX(), (int) coordinates[0].getY());
        var end = new Point((int) coordinates[1].getX(), (int) coordinates[1].getY());
        return new Segment(start, end);
    }
}
