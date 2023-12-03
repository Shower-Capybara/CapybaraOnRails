package com.StationManager.app.storage.repository;

import com.StationManager.app.domain.client.Client;

import java.util.Optional;

public interface IClientRepository extends IRepository<Client> {
    Optional<Client> getById(int id);
}
