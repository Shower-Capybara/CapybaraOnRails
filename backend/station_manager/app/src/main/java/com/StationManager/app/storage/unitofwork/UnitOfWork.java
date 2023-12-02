package com.StationManager.app.storage.unitofwork;

import com.StationManager.app.Main;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class UnitOfWork implements IUnitOfWork {
    protected static final Logger logger = LoggerFactory.getLogger(UnitOfWork.class);
    public abstract void commit();
    public abstract void rollback();
}
