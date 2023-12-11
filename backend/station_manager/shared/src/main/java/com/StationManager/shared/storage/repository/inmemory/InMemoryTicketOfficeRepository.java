package com.StationManager.shared.storage.repository.inmemory;

import com.StationManager.shared.domain.train_station.TicketOffice;
import com.StationManager.shared.storage.repository.ITicketOfficeRepository;

import java.util.Optional;

public class InMemoryTicketOfficeRepository extends InMemoryRepository<TicketOffice> implements ITicketOfficeRepository {

    @Override
    public Optional<TicketOffice> getById(int id) {
        return entities.stream().filter(ticketOffice -> ticketOffice.getId() == id).findFirst();
    }
}
