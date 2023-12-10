package com.StationManager.app.storage.database.converters;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;


@Converter
public class PointConverter implements AttributeConverter<java.awt.Point, org.locationtech.jts.geom.Point> {
    @Override
    public org.locationtech.jts.geom.Point convertToDatabaseColumn(java.awt.Point point) {
        var geometryFactory = new org.locationtech.jts.geom.GeometryFactory();
        return geometryFactory.createPoint(new org.locationtech.jts.geom.CoordinateXY(point.x, point.y));
    }

    @Override
    public java.awt.Point convertToEntityAttribute(org.locationtech.jts.geom.Point s) {
        return new java.awt.Point((int) s.getX(), (int) s.getY());
    }
}
