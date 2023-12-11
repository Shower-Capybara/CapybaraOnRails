package com.StationManager.app.storage.repository.postgres;

import com.StationManager.app.domain.train_station.TrainStation;
import com.StationManager.app.services.mappers.TrainStationMapper;
import com.StationManager.app.storage.database.dto.TrainStationDTO;
import com.StationManager.app.storage.repository.ITrainStationRepository;
import org.hibernate.Session;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class PostgresTrainStationRepository extends PostgresRepository<TrainStation> implements ITrainStationRepository {

    public PostgresTrainStationRepository(Session session) {
        super(session);
    }

    @Override
    public void add(TrainStation trainStation) {
        TrainStationDTO trainStationDTO = TrainStationMapper.toDTO(trainStation);
        session.persist(trainStationDTO);
        this.getById(trainStationDTO.getId()); // update seen
    }

    @Override
    public List<TrainStation> getAll() {
        var stations = this.session.createQuery("from TrainStationDTO", TrainStationDTO.class)
                .getResultList()
                .stream()
                .map(TrainStationMapper::toDomain)
                .collect(Collectors.toList());
        this.seen.addAll(stations);
        return stations;
    }

    @Override
    public Optional<TrainStation> getById(int id) {
        var trainStation = this.session.createQuery("from TrainStationDTO where id = :id", TrainStationDTO.class)
                .setParameter("id", id)
                .getResultList()
                .stream()
                .findFirst()
                .map(TrainStationMapper::toDomain);
        if (trainStation.isEmpty()) {
            return Optional.empty();
        }
        this.seen.add(trainStation.get());
        return trainStation;
    }
}
