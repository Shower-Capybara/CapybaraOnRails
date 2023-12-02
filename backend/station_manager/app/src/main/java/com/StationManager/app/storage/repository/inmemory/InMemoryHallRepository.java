package com.StationManager.app.storage.repository.inmemory;

import com.StationManager.app.domain.train_station.Hall;
import com.StationManager.app.storage.repository.IHallRepository;

import java.util.Objects;
import java.util.Optional;

public class InMemoryHallRepository extends InMemoryRepository<Hall> implements IHallRepository {
    @Override
    public void update(Hall entity) {
        // replace the hall with the same id with the new one
        entities.removeIf(hall -> Objects.equals(hall.getId(), entity.getId()));
        entities.add(entity);
    }

    @Override
    public Optional<Hall> getById(int id) {
        return entities.stream().filter(hall -> hall.getId() == id).findFirst();
    }
}
