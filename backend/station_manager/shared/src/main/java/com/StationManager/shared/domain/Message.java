package com.StationManager.shared.domain;

import com.StationManager.shared.domain.commands.*;
import com.StationManager.shared.domain.events.*;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({
    @JsonSubTypes.Type(value = ClientAddedEvent.class, name = "ClientAddedEvent"),
    @JsonSubTypes.Type(value = ClientBeingServedEvent.class, name = "ClientBeingServedEvent"),
    @JsonSubTypes.Type(value = ClientLeftEvent.class, name = "ClientLeftEvent"),
    @JsonSubTypes.Type(value = ClientMovedEvent.class, name = "ClientMovedEvent"),
    @JsonSubTypes.Type(value = TicketOfficeAddedEvent.class, name = "TicketOfficeAddedEvent"),
    @JsonSubTypes.Type(value = ClientServedEvent.class, name = "ClientServedEvent"),
    @JsonSubTypes.Type(value = ClientBoughtTicketEvent.class, name = "ClientBoughtTicketEvent"),

    @JsonSubTypes.Type(value = AddClientCommand.class, name = "AddClientCommand"),
    @JsonSubTypes.Type(value = AddTicketOfficeCommand.class, name = "AddTicketOfficeCommand"),
    @JsonSubTypes.Type(value = ResumeGeneratorCommand.class, name = "ResumeGenerator"),
    @JsonSubTypes.Type(value = StopGeneratorCommand.class, name = "ClientMovedEvent"),
})
public interface Message { }
