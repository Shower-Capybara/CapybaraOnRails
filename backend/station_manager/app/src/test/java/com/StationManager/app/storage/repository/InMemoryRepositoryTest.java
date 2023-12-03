package com.StationManager.app.storage.repository;

import com.StationManager.app.domain.client.Client;
import com.StationManager.app.domain.client.Privilegy;
import com.StationManager.app.domain.train_station.Direction;
import com.StationManager.app.domain.train_station.Hall;
import com.StationManager.app.domain.train_station.Segment;
import com.StationManager.app.domain.train_station.TicketOffice;
import com.StationManager.app.storage.repository.inmemory.*;
import org.checkerframework.checker.units.qual.A;
import org.eclipse.jetty.util.DateCache;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class InMemoryRepositoryTest {

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
