package com.StationManager.app.storage.repository.inmemory;

import com.StationManager.app.storage.repository.IRepository;

import java.util.ArrayList;
import java.util.List;

public abstract class InMemoryRepository<T> implements IRepository<T> {
    protected final List<T> entities = new ArrayList<>();

    @Override
    public void add(T entity) {
        entities.add(entity);
    }

    @Override
    public void remove(T entity) {
        entities.remove(entity);
    }

    @Override
    public List<T> getAll() {
        return entities;
    }
}
