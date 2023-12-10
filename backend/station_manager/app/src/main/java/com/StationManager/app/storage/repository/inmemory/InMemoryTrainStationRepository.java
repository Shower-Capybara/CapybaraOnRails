package com.StationManager.app.storage.repository.inmemory;

import com.StationManager.shared.domain.train_station.TrainStation;
import com.StationManager.app.storage.repository.ITrainStationRepository;

import java.util.Optional;

public class InMemoryTrainStationRepository extends InMemoryRepository<TrainStation> implements ITrainStationRepository {
    @Override
    public Optional<TrainStation> getById(int id) {
        return entities.stream().filter(trainStation -> trainStation.getId() == id).findFirst();
    }
}
