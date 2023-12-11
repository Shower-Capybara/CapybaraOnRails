package com.StationManager.app.storage.repository.postgres;

import com.StationManager.app.domain.client.Client;
import com.StationManager.app.storage.repository.IClientRepository;
import org.hibernate.Session;

import java.awt.Point;
import java.util.List;
import java.util.Optional;

public class PostgresClientRepository extends PostgresRepository<Client> implements IClientRepository {
    public PostgresClientRepository(Session session) {
        super(session);
    }

    @Override
    public Optional<Client> getById(int id) {
        var client = this.session.createQuery("from Client where id = :id", Client.class)
                .setParameter("id", id)
                .getResultList()
                .stream()
                .findFirst();
        if (client.isEmpty()) {
            return Optional.empty();
        }
        this.seen.add(client.get());
        return client;
    }

    @Override
    public void updatePosition(Integer id, Point point) {
        this.session.createQuery("update Client set position = :point where id = :id")
                .setParameter("point", point)
                .setParameter("id", id)
                .executeUpdate();
        getById(id); // update seen
    }

    @Override
    public List<Client> getAll() {
        var clients = this.session.createQuery("from Client", Client.class)
                .getResultList();
        this.seen.addAll(clients);
        return clients;
    }

    @Override
    public boolean removeById(int id) {
        return this.session.createQuery("delete from Client where id = :id", Client.class)
                .setParameter("id", id)
                .executeUpdate() > 0;
    }
}
