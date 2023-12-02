package com.StationManager.app.storage.repository.inmemory;

import com.StationManager.app.domain.client.Client;
import com.StationManager.app.storage.repository.IClientRepository;

import java.util.Objects;
import java.util.Optional;

public class InMemoryClientRepository extends InMemoryRepository<Client> implements IClientRepository {
    @Override
    public void update(Client entity) {
        // replace the client with the same id with the new one
        entities.removeIf(client -> Objects.equals(client.getId(), entity.getId()));
        entities.add(entity);
    }

    @Override
    public Optional<Client> getById(int id) {
        return entities.stream().filter(client -> client.getId() == id).findFirst();
    }
}
