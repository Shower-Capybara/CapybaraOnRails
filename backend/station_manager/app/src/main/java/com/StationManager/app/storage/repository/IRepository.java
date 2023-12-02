package com.StationManager.app.storage.repository;

import java.util.List;
import java.util.Optional;

public interface IRepository<T> {
    void add(T entity);
    void update(T entity);
    void remove(T entity);
    Optional<T> getById(int id);
    List<T> getAll();
}
