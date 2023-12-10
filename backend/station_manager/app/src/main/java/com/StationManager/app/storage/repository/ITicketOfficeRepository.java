package com.StationManager.app.storage.repository;

import com.StationManager.shared.domain.train_station.TicketOffice;

import java.util.Optional;


public interface ITicketOfficeRepository extends IRepository<TicketOffice> {
    Optional<TicketOffice> getById(int hallId);
}
