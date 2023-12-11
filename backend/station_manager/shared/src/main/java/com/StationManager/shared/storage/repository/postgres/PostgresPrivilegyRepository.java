package com.StationManager.shared.storage.repository.postgres;

import com.StationManager.shared.storage.repository.IPrivilegyRepository;
import com.StationManager.shared.domain.client.Privilegy;
import org.hibernate.Session;

import java.util.List;
import java.util.Optional;

public class PostgresPrivilegyRepository extends PostgresRepository<Privilegy> implements IPrivilegyRepository {
    public PostgresPrivilegyRepository(Session session) {
        super(session);
    }

    @Override
    public Optional<Privilegy> getByType(String type) {
        var privilegy = this.session.createQuery("from Privilegy where type = :type", Privilegy.class)
                .setParameter("type", type)
                .getResultList()
                .stream()
                .findFirst();
        if (privilegy.isEmpty()) {
            return Optional.empty();
        }
        this.seen.add(privilegy.get());
        return privilegy;
    }

//    @Override
//    public boolean removeByType(String  type) {
//        return this.session.createQuery("delete Privilegy where type = :type", Privilegy.class)
//                .setParameter("type", type)
//                .executeUpdate() > 0;
//    }

    @Override
    public List<Privilegy> getAll() {
        var privilegies = this.session.createQuery("from Privilegy", Privilegy.class).getResultList();
        this.seen.addAll(privilegies);
        return privilegies;
    }
}
