package com.StationManager.app.storage.repository;

import com.StationManager.shared.domain.train_station.TrainStation;

import java.util.Optional;


public interface ITrainStationRepository extends IRepository<TrainStation> {
    Optional<TrainStation> getById(int id);
}
