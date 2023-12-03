package com.StationManager.app.storage.repository.inmemory;

import com.StationManager.app.domain.client.Client;
import com.StationManager.app.storage.repository.IClientRepository;

import java.util.Optional;

public class InMemoryClientRepository extends InMemoryRepository<Client> implements IClientRepository {
    @Override
    public Optional<Client> getById(int id) {
        return entities.stream().filter(client -> client.getId() == id).findFirst();
    }
}
