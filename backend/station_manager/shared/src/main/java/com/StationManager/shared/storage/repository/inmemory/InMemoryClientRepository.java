package com.StationManager.shared.storage.repository.inmemory;

import com.StationManager.shared.domain.client.Client;
import com.StationManager.shared.storage.repository.IClientRepository;

import java.awt.*;
import java.util.Optional;

public class InMemoryClientRepository extends InMemoryRepository<Client> implements IClientRepository {
    @Override
    public Optional<Client> getById(int id) {
        return entities.stream().filter(client -> client.getId() == id).findFirst();
    }

    @Override
    public boolean removeById(int id) {
        return entities.removeIf(client -> client.getId() == id);
    }

    @Override
    public void updatePosition(Integer id, Point point) {
        var clientResult = this.getById(id);
        if (clientResult.isEmpty()) throw new IllegalStateException("No client match given Id");
        var client = clientResult.get();
        this.remove(client);
        client.setPosition(point);
        this.add(client);
    }
}
