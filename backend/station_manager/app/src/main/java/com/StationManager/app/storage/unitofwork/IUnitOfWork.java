package com.StationManager.app.storage.unitofwork;

public interface IUnitOfWork extends AutoCloseable {
    void commit();
    void rollback();
}
