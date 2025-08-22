package tech.ada.dto;

import tech.ada.model.Vehicle;
import tech.ada.model.VehicleStatus;

public record VehicleResponseBody(
        Long id,
        String model,
        VehicleStatus status,
        int year,
        String engine,
        String carTitle
) {

    public VehicleResponseBody(Vehicle vehicle) {

        this(vehicle.getId(),vehicle.getModel(), vehicle.getStatus(), vehicle.getYear(), vehicle.getEngine(),
                vehicle.getModel() + " " + vehicle.getYear() + " " + vehicle.getEngine());
    }
}