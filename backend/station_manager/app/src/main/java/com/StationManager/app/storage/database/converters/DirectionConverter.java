package com.StationManager.app.storage.database.converters;

import com.StationManager.app.domain.train_station.Direction;
import jakarta.persistence.AttributeConverter;

import java.awt.*;

public class DirectionConverter implements AttributeConverter<Direction, String>  {
    @Override
    public String convertToDatabaseColumn(Direction direction) {
        return direction.toString();
    }

    @Override
    public Direction convertToEntityAttribute(String s) {
        return Direction.valueOf(s);
    }
}
