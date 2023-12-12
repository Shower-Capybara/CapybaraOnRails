package com.StationManager.app.services.handlers.commands;

import com.StationManager.shared.domain.commands.AddEntranceCommand;
import com.StationManager.shared.domain.commands.AddTicketOfficeCommand;
import com.StationManager.shared.domain.train_station.Segment;
import com.StationManager.shared.services.unitofwork.UnitOfWork;

public class AddEntranceCommandHandler implements CommandHandler<AddEntranceCommand> {
    @Override
    public void handle(AddEntranceCommand command, UnitOfWork uow) {
        Segment entrance = command.entrance;
        var hallId = command.hallId;
        uow.getHallRepository().getById(hallId).ifPresent(hall -> hall.setEntrance(entrance));
    }
}
