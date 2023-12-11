package com.StationManager.app.storage.database.dto;

public class TrainStationDTO {
    private Integer id;
    private HallDTO hall;

    public TrainStationDTO() {}

    public TrainStationDTO(Integer id, HallDTO hall) {
        this.id = id;
        this.hall = hall;
    }

    public Integer getId() { return this.id; }
    public HallDTO getHall() { return this.hall; }

    public void setId(Integer id) { this.id = id; }
    public void setHall(HallDTO hall) { this.hall = hall; }
}
