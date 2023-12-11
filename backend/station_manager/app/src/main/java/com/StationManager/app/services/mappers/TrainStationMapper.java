package com.StationManager.app.services.mappers;

public class TrainStationMapper {
    public static com.StationManager.app.domain.train_station.TrainStation toDomain(com.StationManager.app.storage.database.dto.TrainStationDTO dto) {
        return new com.StationManager.app.domain.train_station.TrainStation(
                dto.getId(),
                HallMapper.convertToDomain(dto.getHall())
        );
    }

    public static com.StationManager.app.storage.database.dto.TrainStationDTO toDTO(com.StationManager.app.domain.train_station.TrainStation domain) {
        return new com.StationManager.app.storage.database.dto.TrainStationDTO(
                domain.getId(),
                HallMapper.convertToDTO(domain.getHall())
        );
    }
}
