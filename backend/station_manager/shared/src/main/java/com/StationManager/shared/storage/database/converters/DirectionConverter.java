package com.StationManager.shared.storage.database.converters;

import com.StationManager.shared.domain.train_station.Direction;
import jakarta.persistence.AttributeConverter;

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
