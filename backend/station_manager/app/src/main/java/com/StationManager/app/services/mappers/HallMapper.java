package com.StationManager.app.services.mappers;

import com.StationManager.app.domain.train_station.Hall;
import com.StationManager.app.domain.train_station.Segment;
import com.StationManager.app.domain.train_station.TicketOffice;
import com.StationManager.app.storage.database.dto.Entrance;
import com.StationManager.app.storage.database.dto.HallDTO;
import com.StationManager.app.storage.database.dto.HallsTicketOffices;

import java.util.LinkedList;

public class HallMapper {
    public static HallDTO convertToDTO(Hall hall) {
        HallDTO hallDTO = new HallDTO();
        hallDTO.setId(hall.getId());
        hallDTO.setSegment(hall.getSegment());

        LinkedList<Entrance> entrances = new LinkedList<>();
        LinkedList<HallsTicketOffices> ticketOffices = new LinkedList<>();

        for (Segment entrance : hall.getEntrances()) {
            entrances.add(new Entrance(
                hallDTO,
                entrance
            ));
        }

        for (TicketOffice ticketOffice : hall.getTicketOffices()) {
            ticketOffices.add(new HallsTicketOffices(
                hallDTO,
                TicketOfficeMapper.convertToDTO(ticketOffice)
            ));
        }

        hallDTO.setEntrances(entrances);
        hallDTO.setTicketOffices(ticketOffices);

        return hallDTO;
    }

    public static Hall convertToDomain(HallDTO hallDTO) {
        Hall hall = new Hall();
        hall.setId(hallDTO.getId());
        hall.setSegment(hallDTO.getSegment());

        for (Entrance entrance : hallDTO.getEntrances()) {
            hall.getEntrances().add(entrance.getSegment());
        }

        for (HallsTicketOffices hallsTicketOffices : hallDTO.getTicketOffices()) {
            hall.getTicketOffices().add(TicketOfficeMapper.convertToDomain(hallsTicketOffices.getTicketOffice()));
        }

        return hall;
    }
}
