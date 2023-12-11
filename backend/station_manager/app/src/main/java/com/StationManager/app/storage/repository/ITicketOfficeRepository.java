package com.StationManager.app.storage.repository;

import com.StationManager.app.domain.train_station.TicketOffice;

import java.util.Optional;


public interface ITicketOfficeRepository extends IRepository<TicketOffice> {
    Optional<TicketOffice> getById(int ticketOfficeId);
}
