package tech.ada.resource;

import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import tech.ada.dto.VehicleRequestBody;
import tech.ada.model.VehicleStatus;

class VehicleResourceTest {

    @Test
    void shouldCreateAVehicle() {

        VehicleResource vehicleResource = new VehicleResource();

        VehicleRequestBody vehicleRequestBody = new VehicleRequestBody();
        vehicleRequestBody.setStatus(VehicleStatus.AVAILABLE);
        vehicleRequestBody.setModel("Mobi");
        vehicleRequestBody.setYear(2018);
        vehicleRequestBody.setEngine("1.0");

        Response response = vehicleResource.create(vehicleRequestBody);

        Assertions.assertEquals(response.getStatus(), 201);
    }
}