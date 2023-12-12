package com.StationManager.shared.domain.commands;

import com.StationManager.shared.domain.train_station.Segment;
import com.StationManager.shared.storage.database.dto.Entrance;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AddEntranceCommand extends Command {
    public Segment entrance;
    public Integer hallId;

    @JsonCreator
    public AddEntranceCommand(
        @JsonProperty(value = "entrance", required = true) Segment entrance,
        @JsonProperty(value = "hallId", required = true) Integer hallId
    ) {
        this.entrance = entrance;
        this.hallId = hallId;
    }
}