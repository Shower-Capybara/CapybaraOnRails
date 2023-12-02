package com.StationManager.app.storage.repository;

import com.StationManager.app.domain.client.Privilegy;

import java.util.Optional;

public interface IPrivilegyRepository extends IRepository<Privilegy> {
    Optional<Privilegy> getByType(String type);  // no getByID because type is unique (getByID always null)
}
