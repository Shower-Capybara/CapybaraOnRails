package com.StationManager.app.storage.repository.inmemory;

import com.StationManager.app.storage.repository.IRepository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public abstract class InMemoryRepository<T> implements IRepository<T> {
    protected final Set<T> seen = new HashSet<>();
    protected final List<T> entities = new ArrayList<>();

    @Override
    public void add(T entity) {
        entities.add(entity);
        seen.add(entity);
    }

    @Override
    public void remove(T entity) {
        entities.remove(entity);
    }

    @Override
    public List<T> getAll() {
        return entities;
    }

    @Override
    public Set<T> getSeen() {
        return seen;
    }
}
