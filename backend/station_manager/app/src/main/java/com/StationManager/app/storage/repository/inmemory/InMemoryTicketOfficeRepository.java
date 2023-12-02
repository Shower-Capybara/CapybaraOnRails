package com.StationManager.app.storage.repository.inmemory;

import com.StationManager.app.domain.train_station.TicketOffice;
import com.StationManager.app.storage.repository.ITicketOfficeRepository;

import java.util.Objects;
import java.util.Optional;

public class InMemoryTicketOfficeRepository extends InMemoryRepository<TicketOffice> implements ITicketOfficeRepository {
    @Override
    public void update(TicketOffice entity) {
        // replace the ticket office with the same id with the new one
        entities.removeIf(ticketOffice -> Objects.equals(ticketOffice.getId(), entity.getId()));
        entities.add(entity);
    }

    @Override
    public Optional<TicketOffice> getById(int id) {
        return entities.stream().filter(ticketOffice -> ticketOffice.getId() == id).findFirst();
    }
}
