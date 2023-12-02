package com.StationManager.app.storage.repository.inmemory;

import com.StationManager.app.domain.client.Privilegy;
import com.StationManager.app.domain.train_station.Hall;
import com.StationManager.app.storage.repository.IHallRepository;
import com.StationManager.app.storage.repository.IPrivilegyRepository;

import java.util.Objects;
import java.util.Optional;

public class InMemoryPrivilegyRepository extends InMemoryRepository<Privilegy> implements IPrivilegyRepository {
    @Override
    public void update(Privilegy entity) {
        // replace the hall with the same id with the new one
        entities.removeIf(privilegy -> Objects.equals(privilegy.getType(), entity.getType()));
        entities.add(entity);
    }

    @Override
    public Optional<Privilegy> getByType(String type) {
        return entities.stream().filter(privilegy -> privilegy.getType().equals(type)).findFirst();
    }

    @Override
    public Optional<Privilegy> getById(int id) {
        logger.warn("ID is not used in Privilegy repository. Use Type instead.");
        return Optional.empty();
    }

}
