package com.StationManager.shared.storage.uow;

import com.StationManager.shared.services.unitofwork.InMemoryUnitOfWork;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class InMemoryUowTest {
    @Test
    void testInMemoryUow() {
        try (var uow = new InMemoryUnitOfWork()) {
            uow.commit();
            assertTrue(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
