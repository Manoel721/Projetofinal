package tech.ada.dto;

import tech.ada.model.VehicleStatus;

public record UpdateVehicleStatusRequestBody(
        VehicleStatus status
) {
}
