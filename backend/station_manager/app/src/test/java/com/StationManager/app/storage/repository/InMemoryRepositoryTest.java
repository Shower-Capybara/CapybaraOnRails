package com.StationManager.app.storage.repository;

import com.StationManager.app.storage.repository.inmemory.*;
import org.junit.jupiter.api.Test;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryRepositoryTest {

    record FakeEntity(int id) {
    }

    static class FakeRepository extends InMemoryRepository<FakeEntity> {
    }

    @Test
    void testAdd() {
        var repository = new FakeRepository();
        var entities = prepareEntities();

        entities.forEach(repository::add);

        var actual = repository.getAll();
        assertEquals(entities.size(), actual.size());
        assertTrue(actual.containsAll(entities));
    }

    @Test
    void testRemove() {
        var repository = new FakeRepository();
        var entities = prepareEntities();

        entities.forEach(repository::add);

        var entityToRemove = entities.get(1);
        repository.remove(entityToRemove);

        var actual = repository.getAll();
        assertEquals(entities.size() - 1, actual.size());
        assertTrue(actual.containsAll(List.of(entities.get(0), entities.get(2))));
    }

    static List<FakeEntity> prepareEntities() {
        return List.of(
                new FakeEntity(1),
                new FakeEntity(2),
                new FakeEntity(3)
        );
    }


}
