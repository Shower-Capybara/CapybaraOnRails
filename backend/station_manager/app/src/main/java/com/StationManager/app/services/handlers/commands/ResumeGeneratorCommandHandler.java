package com.StationManager.app.services.handlers.commands;

import com.StationManager.shared.domain.commands.ResumeGeneratorCommand;
import com.StationManager.app.services.unitofwork.UnitOfWork;
import kotlin.NotImplementedError;

public class ResumeGeneratorCommandHandler implements CommandHandler<ResumeGeneratorCommand> {
    @Override
    public void handle(ResumeGeneratorCommand command, UnitOfWork uow) {
        throw new NotImplementedError();
    }

}
