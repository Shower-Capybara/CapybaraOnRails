package com.StationManager.app.storage.repository;

import com.StationManager.shared.domain.client.Privilegy;

import java.util.Optional;

public interface IPrivilegyRepository extends IRepository<Privilegy> {
    Optional<Privilegy> getByType(String type);  // type is unique
}
