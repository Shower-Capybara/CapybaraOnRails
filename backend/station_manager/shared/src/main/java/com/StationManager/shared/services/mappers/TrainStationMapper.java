package com.StationManager.shared.services.mappers;

import com.StationManager.shared.storage.database.dto.TrainStationDTO;
import com.StationManager.shared.domain.train_station.TrainStation;

public class TrainStationMapper {
    public static TrainStation toDomain(TrainStationDTO dto) {
        return new TrainStation(
                dto.getId(),
                HallMapper.convertToDomain(dto.getHall())
        );
    }

    public static TrainStationDTO toDTO(TrainStation domain) {
        return new TrainStationDTO(
                domain.getId(),
                HallMapper.convertToDTO(domain.getHall())
        );
    }
}
