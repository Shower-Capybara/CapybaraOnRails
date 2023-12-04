package com.StationManager.app.storage.unitofwork;

import com.StationManager.app.Main;
import com.StationManager.app.domain.client.Client;
import com.StationManager.app.domain.client.Privilegy;
import com.StationManager.app.domain.events.Event;
import com.StationManager.app.domain.train_station.Hall;
import com.StationManager.app.domain.train_station.TicketOffice;
import com.StationManager.app.domain.train_station.TrainStation;
import com.StationManager.app.storage.repository.IRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public abstract class UnitOfWork implements IUnitOfWork {
    protected static final Logger logger = LoggerFactory.getLogger(UnitOfWork.class);
}
