package com.StationManager.app.storage.repository;

import java.util.List;
import java.util.Set;

public interface IRepository<T> {
    void add(T entity);
    void remove(T entity);
    List<T> getAll();
    Set<T> getSeen();
}
