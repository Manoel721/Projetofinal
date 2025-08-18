package tech.ada.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class VehicleTest {


    private Long id;
    private String model;
    private VehicleStatus status;
    private int year;
    private String engine;

    /**
     * 1. Quando um vehicle for criado ele deve possuir um ID válido (>=1) ok
     * 2. Quando um vehicle for criado ele deve ser criado com o status o AVAILABLE
     * 3. Quando o Vehicle AVAILABLE ele só pode ir pra RENTED ou UNDER_MAINTENANCE
     * 4. Quando eu criar um Vehicle ele não pode ter model, year, engine (vazios ou nulos)
     * 5. (opcional) o ano deve ser atual (2025 <= year)
     */
    @Test
    void shouldCreateVehicleWithValidID() {
        Vehicle vehicle = new Vehicle("Mobi", 2025, "1.0");

        // assertions
        Assertions.assertTrue(vehicle.getId() >= 1);
        Assertions.assertNotNull(vehicle.getModel());
    }

    @Test
    void shouldCreateVehicleWithStatusAvailable() {
        Vehicle vehicle = new Vehicle("Mobi", 2025, "1.0");

        // assertions
        Assertions.assertEquals(VehicleStatus.AVAILABLE, vehicle.getStatus());
    }

    @Test
    void shouldChangeStatusToRentedOrUnderMaintenanceWhenCurrentStatusIsAvailable() {
        Vehicle vehicle = new Vehicle("Mobi", 2025, "1.0");

        vehicle.setStatus(VehicleStatus.UNDER_MAINTENANCE);

        Assertions.assertEquals(VehicleStatus.UNDER_MAINTENANCE, vehicle.getStatus());
    }

    @Test
    void shouldThrowsChangeToRentedWhenTheCurrentStatusIsUnderMaintenance() {
        Vehicle vehicle = new Vehicle("Mobi", 2025, "1.0");
        vehicle.setStatus(VehicleStatus.UNDER_MAINTENANCE);

        String message = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            vehicle.setStatus(VehicleStatus.RENTED);
        }).getMessage();

        Assertions.assertTrue(message.contains("Validation error"));
    }

    @Test
    void shouldCreateVehicleWithoutNullOrEmptyFields() {

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new Vehicle(null, 2025, "1.0");
        });

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new Vehicle("", 2025, "1.0");
        });

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new Vehicle("    ", 2025, "1.0");
        });

//        Assertions.assertThrows(IllegalArgumentException.class, () -> {
//            new Vehicle("  4     ", 2025, "1.0");
//        });
//
//        Assertions.assertThrows(IllegalArgumentException.class, () -> {
//            new Vehicle(null, 2025, "");
//        });
    }
}
