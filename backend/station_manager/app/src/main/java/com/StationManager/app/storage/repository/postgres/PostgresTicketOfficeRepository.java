package com.StationManager.app.storage.repository.postgres;

import com.StationManager.app.domain.client.Client;
import com.StationManager.app.domain.train_station.TicketOffice;
import com.StationManager.app.services.mappers.TicketOfficeMapper;
import com.StationManager.app.storage.database.dto.ClientPositions;
import com.StationManager.app.storage.database.dto.TicketOfficeDTO;
import com.StationManager.app.storage.repository.ITicketOfficeRepository;
import org.hibernate.Session;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class PostgresTicketOfficeRepository extends PostgresRepository<TicketOffice> implements ITicketOfficeRepository {
    public PostgresTicketOfficeRepository(Session session) {
        super(session);
    }

    @Override
    public void add(TicketOffice entity) {
        TicketOfficeDTO ticketOfficeDTO = TicketOfficeMapper.convertToDTO(entity);
        session.persist(ticketOfficeDTO);
    }

    @Override
    public Optional<TicketOffice> getById(int ticketOfficeId) {
        var ticketOffice = this.session.createQuery("from TicketOfficeDTO where id = :id", TicketOfficeDTO.class)
                .setParameter("id", ticketOfficeId)
                .getResultList()
                .stream()
                .findFirst()
                .map(TicketOfficeMapper::convertToDomain);
        if (ticketOffice.isEmpty()) {
            return Optional.empty();
        }
        this.seen.add(ticketOffice.get());
        return ticketOffice;
    }

    @Override
    public List<TicketOffice> getAll() {
        var ticketOffices = this.session.createQuery("from TicketOfficeDTO", TicketOfficeDTO.class)
                .getResultList()
                .stream()
                .map(TicketOfficeMapper::convertToDomain)
                .toList();
        this.seen.addAll(ticketOffices);
        return ticketOffices;
    }

    public boolean removeById(int id) {
        return this.session.createQuery("delete TicketOfficeDTO where id = :id", TicketOfficeDTO.class)
                .setParameter("id", id)
                .executeUpdate() > 0;
    }

    public boolean addClientToQueue(Client client, int ticketOfficeId) {
        var ticketOffice = this.getById(ticketOfficeId);
        if (ticketOffice.isEmpty()) {
            return false;
        }

        var ticketOfficeDTO = TicketOfficeMapper.convertToDTO(ticketOffice.get());

        var clientPosition = new ClientPositions(client, ticketOfficeDTO, ticketOfficeDTO.getQueuePositions().size());
        ticketOfficeDTO.getQueuePositions().add(clientPosition);
        session.persist(clientPosition);
        this.getById(ticketOfficeId); // update seen
        return true;
    }
}
