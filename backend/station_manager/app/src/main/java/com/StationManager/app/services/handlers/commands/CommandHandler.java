package com.StationManager.app.services.handlers.commands;

import com.StationManager.shared.domain.commands.Command;
import com.StationManager.app.services.unitofwork.UnitOfWork;

public interface CommandHandler<T extends Command> {
    void handle(T command, UnitOfWork uow);
}