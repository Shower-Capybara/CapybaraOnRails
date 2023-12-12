package com.StationManager.shared.domain.events;

import com.StationManager.shared.domain.train_station.Segment;
import com.StationManager.shared.storage.database.dto.Entrance;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)

public class EntranceAddedEvent extends Event{
    public Segment entrance;
    @JsonCreator
    public EntranceAddedEvent(
        @JsonProperty(value = "entrance", required = true) Segment entrance
    ) {
        this.entrance = entrance;
    }
}
