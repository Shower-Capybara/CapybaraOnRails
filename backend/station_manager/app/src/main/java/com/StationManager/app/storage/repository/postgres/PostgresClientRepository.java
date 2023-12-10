package com.StationManager.app.storage.repository.postgres;

import com.StationManager.app.domain.client.Client;
import com.StationManager.app.storage.repository.IClientRepository;
import org.hibernate.Session;

import java.awt.*;
import java.util.Optional;

public class PostgresClientRepository extends PostgresRepository<Client> implements IClientRepository {
    public PostgresClientRepository(Session session) {
        super(session);
    }

    @Override
    public Optional<Client> getById(int id) {
        return this.session.createQuery("from Client where id = :id", Client.class)
                .setParameter("id", id)
                .getResultList()
                .stream()
                .findFirst();
    }

    @Override
    public void updatePosition(Integer id, Point point) {
        this.session.createQuery("update Client set position = :point where id = :id")
                .setParameter("point", point)
                .setParameter("id", id)
                .executeUpdate();
    }
}
