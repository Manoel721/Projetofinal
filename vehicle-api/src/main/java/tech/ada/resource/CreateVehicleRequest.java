package tech.ada.resource;

import jakarta.persistence.Column;
import tech.ada.model.VehicleStatus;

public class CreateVehicleRequest {
    private String model;
    private VehicleStatus status;
    @Column(name = "vehicle_year")
    private int year;
    private String engine;

    public String model() {
        return model;
    }
    public String engine() {
        return engine;
    }
    public int year() {
        return year;
    }
}
