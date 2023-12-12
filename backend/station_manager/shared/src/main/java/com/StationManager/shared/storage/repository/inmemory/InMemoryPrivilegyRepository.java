package com.StationManager.shared.storage.repository.inmemory;

import com.StationManager.shared.domain.client.Privilegy;
import com.StationManager.shared.storage.repository.IPrivilegyRepository;

import java.util.Optional;

public class InMemoryPrivilegyRepository extends InMemoryRepository<Privilegy> implements IPrivilegyRepository {
    @Override
    public Optional<Privilegy> getByType(String type) {
        return entities.stream().filter(privilegy -> privilegy.getType().equals(type)).findFirst();
    }
}
