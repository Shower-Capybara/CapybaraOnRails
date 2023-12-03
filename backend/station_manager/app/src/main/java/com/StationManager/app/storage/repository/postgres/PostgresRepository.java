package com.StationManager.app.storage.repository.postgres;

import com.StationManager.app.storage.repository.IRepository;
import org.hibernate.Session;

import java.util.List;
import java.util.Optional;

/**
 * Example of a repository implementation for Postgres database.
 * This class is not implemented yet.
 * Session is used to do all the database operations. No commits are allowed here.
 */
public class PostgresRepository<T> implements IRepository<T> {
    private Session session;

    public PostgresRepository(Session session) {
        this.session = session;
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void add(T entity) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void remove(T entity) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public List<T> getAll() {
        return null;
    }
}
