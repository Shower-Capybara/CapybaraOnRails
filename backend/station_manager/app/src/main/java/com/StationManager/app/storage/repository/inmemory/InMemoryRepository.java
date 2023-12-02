package com.StationManager.app.storage.repository.inmemory;

import com.StationManager.app.Main;
import com.StationManager.app.storage.repository.IRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class InMemoryRepository<T> implements IRepository<T> {
    protected static final Logger logger = LoggerFactory.getLogger(InMemoryRepository.class);
    protected final List<T> entities = new ArrayList<>();

    @Override
    public void add(T entity) {
        entities.add(entity);
    }

    @Override
    public void update(T entity) {
        throw new UnsupportedOperationException("Update method must be implemented in child class");
    }

    @Override
    public void remove(T entity) {
        entities.remove(entity);
    }

    @Override
    public Optional<T> getById(int id) {
        return Optional.empty();
    }

    @Override
    public List<T> getAll() {
        return entities;
    }
}
