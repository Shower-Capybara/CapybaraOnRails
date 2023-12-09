package com.StationManager.app.storage.repository.inmemory;

import com.StationManager.shared.domain.train_station.Hall;
import com.StationManager.app.storage.repository.IHallRepository;

import java.util.Optional;

public class InMemoryHallRepository extends InMemoryRepository<Hall> implements IHallRepository {
    @Override
    public Optional<Hall> getById(int id) {
        return entities.stream().filter(hall -> hall.getId() == id).findFirst();
    }
}
