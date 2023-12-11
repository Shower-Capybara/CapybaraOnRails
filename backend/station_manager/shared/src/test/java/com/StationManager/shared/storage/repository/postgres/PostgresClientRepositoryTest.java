package com.StationManager.shared.storage.repository.postgres;

import com.StationManager.shared.storage.database.utils.HibernateUtil;
import com.StationManager.shared.domain.client.Client;
import org.junit.jupiter.api.*;

import java.awt.*;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class PostgresClientRepositoryTest {
    private PostgresClientRepository postgresClientRepository;

    @BeforeEach
    void setUp() {
        var session = HibernateUtil.getSessionFactory().openSession();

        postgresClientRepository = new PostgresClientRepository(session);
        if (!session.getTransaction().isActive()) session.beginTransaction();
    }

    @AfterEach
    void rollback() {
        postgresClientRepository.session.getTransaction().rollback();
    }

    @AfterAll
    static void tearDown() {
        HibernateUtil.getSessionFactory().close();
    }

    @Test
    void getById() {
        int id = 1;

        Optional<Client> client = postgresClientRepository.getById(id);

        assertTrue(client.isPresent());
        assertEquals(id, client.get().getId());
    }

    @Test
    void updatePosition() {
        int clientId = 1;
        var newPosition = new java.awt.Point(12, 15);

        postgresClientRepository.updatePosition(clientId, newPosition);

        var client = postgresClientRepository.getById(clientId);
        if (client.isEmpty()) {
            fail("Client not found");
        }

        var clientPosition = client.get().getPosition();

        assertEquals(newPosition, clientPosition);
    }

    @Test
    void add() {
        var client = new Client("Tymofii", "Nasobko", null, new Point(3, 4));

        postgresClientRepository.add(client);

        var clientFromDb = postgresClientRepository.getById(client.getId());

        assertTrue(clientFromDb.isPresent());
        assertEquals(client, clientFromDb.get());
    }

    @Test
    void getAll() {
        var clients = postgresClientRepository.getAll();

        assertNotNull(clients);
        assertFalse(clients.isEmpty());
    }
}