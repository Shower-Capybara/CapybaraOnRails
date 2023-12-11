package com.StationManager.shared.services.mappers;

import com.StationManager.shared.storage.database.dto.ClientPositions;
import com.StationManager.shared.storage.database.dto.TicketOfficeDTO;
import com.StationManager.shared.domain.train_station.TicketOffice;

import java.util.LinkedList;

public class TicketOfficeMapper {
    public static TicketOfficeDTO convertToDTO(TicketOffice ticketOffice) {
        TicketOfficeDTO ticketOfficeDTO = new TicketOfficeDTO();
        ticketOfficeDTO.setId(ticketOffice.getId());
        ticketOfficeDTO.setSegment(ticketOffice.getSegment());
        ticketOfficeDTO.setTimeToServeTicket(ticketOffice.getTimeToServeTicket());
        ticketOfficeDTO.setIsClosed(ticketOffice.getIsClosed());
        ticketOfficeDTO.setIsReserved(ticketOffice.getIsReserved());
        ticketOfficeDTO.setDirection(ticketOffice.getDirection());

        LinkedList<ClientPositions> queuePositions = new LinkedList<>();

        for (int i = 0; i < ticketOffice.getQueue().size(); i++) {
            queuePositions.add(new ClientPositions(
                ticketOffice.getQueue().get(i),
                ticketOfficeDTO,
                i
            ));
        }

        ticketOfficeDTO.setQueuePositions(queuePositions);
        return ticketOfficeDTO;
    }

    public static TicketOffice convertToDomain(TicketOfficeDTO ticketOfficeDTO) {
        TicketOffice ticketOffice = new TicketOffice();
        ticketOffice.setId(ticketOfficeDTO.getId());
        ticketOffice.setSegment(ticketOfficeDTO.getSegment());
        ticketOffice.setTimeToServeTicket(ticketOfficeDTO.getTimeToServeTicket());
        ticketOffice.setIsClosed(ticketOfficeDTO.getIsClosed());
        ticketOffice.setIsReserved(ticketOfficeDTO.getIsReserved());
        ticketOffice.setDirection(ticketOfficeDTO.getDirection());

        for (ClientPositions clientPositions : ticketOfficeDTO.getQueuePositions()) {
            ticketOffice.getQueue().add(clientPositions.getClient());
        }

        return ticketOffice;
    }
}
