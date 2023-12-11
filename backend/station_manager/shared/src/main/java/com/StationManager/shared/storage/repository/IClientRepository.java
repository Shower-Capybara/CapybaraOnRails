package com.StationManager.shared.storage.repository;

import com.StationManager.shared.domain.client.Client;

import java.awt.*;
import java.util.Optional;

public interface IClientRepository extends IRepository<Client> {
    Optional<Client> getById(int id);
    boolean removeById(int id);
    void updatePosition(Integer id, Point point);
}
