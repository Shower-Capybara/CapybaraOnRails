package com.StationManager.app.storage.repository;

import com.StationManager.app.domain.train_station.Hall;

import java.util.Optional;

public interface IHallRepository extends IRepository<Hall> {
    Optional<Hall> getById(int id);
}
