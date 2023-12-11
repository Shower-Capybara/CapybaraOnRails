package com.StationManager.shared.storage.repository.postgres;

import com.StationManager.shared.storage.repository.IRepository;
import org.hibernate.Session;

import java.util.*;

/**
 * Example of a repository implementation for Postgres database.
 * This class is not implemented yet.
 * Session is used to do all the database operations. No commits are allowed here.
 */
public abstract class PostgresRepository<T> implements IRepository<T> {
    protected final Set<T> seen = new HashSet<>();
    protected Session session;

    protected PostgresRepository(Session session) {
        this.session = session;
    }

    @Override
    public void add(T entity) {
        session.persist(entity);
        seen.add(entity);
    }

    @Override
    public void remove(T entity) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public List<T> getAll() {
        return new LinkedList<>();
    }

    @Override
    public Set<T> getSeen() {
        return this.seen;
    }
}
