package com.StationManager.app.services.handlers.commands;

import com.StationManager.app.domain.commands.StopGeneratorCommand;
import com.StationManager.app.services.unitofwork.UnitOfWork;
import kotlin.NotImplementedError;

public class StopGeneratorCommandHandler implements CommandHandler<StopGeneratorCommand> {
    @Override
    public void handle(StopGeneratorCommand command, UnitOfWork uow) {
        throw new NotImplementedError();
    }
}
