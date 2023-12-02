package com.StationManager.app.storage.repository.inmemory;

import com.StationManager.app.domain.train_station.TrainStation;
import com.StationManager.app.storage.repository.ITrainStationRepository;

import java.util.Objects;
import java.util.Optional;

public class InMemoryTrainStationRepository extends InMemoryRepository<TrainStation> implements ITrainStationRepository {

    @Override
    public void update(TrainStation entity) {
        // replace the train station with the same id with the new one
        entities.removeIf(trainStation -> Objects.equals(trainStation.getId(), entity.getId()));
        entities.add(entity);
    }

    @Override
    public Optional<TrainStation> getById(int id) {
        return entities.stream().filter(trainStation -> trainStation.getId() == id).findFirst();
    }
}
