package com.StationManager.shared.domain.events;

import com.StationManager.shared.domain.client.Client;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.sql.Timestamp;

@JsonIgnoreProperties(ignoreUnknown = true)
public class LogRecordEvent extends Event {
    public Client client;
    public Timestamp startTime;
    public Timestamp endTime;

    @JsonCreator
    public LogRecordEvent(
        @JsonProperty(value = "client", required = true) Client client,
        @JsonProperty(value = "startTime", required = true) Timestamp startTime,
        @JsonProperty(value = "endTime", required = true) Timestamp endTime
    ) {
        super();
        this.client = client;
        this.startTime = startTime;
        this.endTime = endTime;
    }
}
