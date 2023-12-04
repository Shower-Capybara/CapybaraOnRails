package com.StationManager.app.services.unitofwork;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class UnitOfWork implements IUnitOfWork {
    protected static final Logger logger = LoggerFactory.getLogger(UnitOfWork.class);
}
