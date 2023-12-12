package com.StationManager.app.services.handlers.commands;

import com.StationManager.shared.domain.commands.ReassignClientCommand;
import com.StationManager.shared.services.unitofwork.UnitOfWork;

public class ReassignClientCommandHandler implements CommandHandler<ReassignClientCommand> {
    @Override
    public void handle(ReassignClientCommand command, UnitOfWork uow) {
        var hallId = command.hallId;
        var ticketOfficeId = command.ticketOfficeId;
        var client = command.client;
        uow.getTicketOfficeRepository().getById(ticketOfficeId).get().getQueue().remove(client);
        uow.getHallRepository().getById(hallId).ifPresent(hall -> hall.assignToTicketOffice(client));
    }
}
