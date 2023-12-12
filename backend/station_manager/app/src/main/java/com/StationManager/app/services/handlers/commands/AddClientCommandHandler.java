package com.StationManager.app.services.handlers.commands;

import com.StationManager.shared.domain.commands.AddClientCommand;
import com.StationManager.shared.services.unitofwork.UnitOfWork;

public class AddClientCommandHandler implements CommandHandler<AddClientCommand> {
    @Override
    public void handle(AddClientCommand command, UnitOfWork uow) {
        var client = command.client;
        var hallId = command.hallId;
        uow.getClientRepository().add(client);
        uow.getHallRepository().getById(hallId).ifPresent(hall -> hall.addClient(client));

    }

}
