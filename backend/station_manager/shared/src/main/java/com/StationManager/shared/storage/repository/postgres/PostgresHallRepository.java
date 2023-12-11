package com.StationManager.shared.storage.repository.postgres;

import com.StationManager.shared.services.mappers.HallMapper;
import com.StationManager.shared.storage.database.dto.HallDTO;
import com.StationManager.shared.storage.repository.IHallRepository;
import com.StationManager.shared.domain.train_station.Hall;
import org.hibernate.Session;

import java.util.List;
import java.util.Optional;

public class PostgresHallRepository extends PostgresRepository<Hall> implements IHallRepository {

    public PostgresHallRepository(Session session) {
        super(session);
    }

    @Override
    public List<Hall> getAll() {
        List<HallDTO> hallDTOs = session.createQuery("from HallDTO", HallDTO.class).list();
        var halls = hallDTOs.stream().map(HallMapper::convertToDomain).toList();
        this.seen.addAll(halls);
        return halls;
    }

    @Override
    public void add(Hall hall) {
        HallDTO hallDTO = HallMapper.convertToDTO(hall);
        session.save(hallDTO);
        this.seen.add(HallMapper.convertToDomain(hallDTO));
    }

    @Override
    public Optional<Hall> getById(int id) {
        var hall = this.session.createQuery("from HallDTO where id = :id", HallDTO.class)
                .setParameter("id", id)
                .uniqueResultOptional()
                .map(HallMapper::convertToDomain);
        if (hall.isEmpty()) {
            return Optional.empty();
        }
        this.seen.add(hall.get());
        return hall;
    }
}
