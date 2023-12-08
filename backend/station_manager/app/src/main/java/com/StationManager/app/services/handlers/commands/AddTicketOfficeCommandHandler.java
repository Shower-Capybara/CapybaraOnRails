package com.StationManager.app.services.handlers.commands;

import com.StationManager.app.domain.commands.AddTicketOfficeCommand;
import com.StationManager.app.services.unitofwork.UnitOfWork;

public class AddTicketOfficeCommandHandler implements CommandHandler<AddTicketOfficeCommand> {
    @Override
    public void handle(AddTicketOfficeCommand command, UnitOfWork uow) {
        var ticketOffice = command.ticketOffice;
        var hallId = command.hallId;
        uow.getTicketOfficeRepository().add(ticketOffice);
        uow.getHallRepository().getById(hallId).ifPresent(hall -> hall.addTicketOffice(ticketOffice));
    }
}
