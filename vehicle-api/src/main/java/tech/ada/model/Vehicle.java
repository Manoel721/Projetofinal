package tech.ada.model;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Entity
public class Vehicle extends PanacheEntityBase {

    private static final Map<VehicleStatus, Set<VehicleStatus>> VEHICLE_STATUS = new HashMap<>() {
    };

    static {
        VEHICLE_STATUS.put(VehicleStatus.AVAILABLE, Set.of(VehicleStatus.RENTED, VehicleStatus.UNDER_MAINTENANCE));
        VEHICLE_STATUS.put(VehicleStatus.RENTED, Set.of(VehicleStatus.AVAILABLE, VehicleStatus.UNDER_MAINTENANCE));
        VEHICLE_STATUS.put(VehicleStatus.UNDER_MAINTENANCE, Set.of(VehicleStatus.AVAILABLE));
    }

    @Id
    @GeneratedValue
    public Long id;
    private String model;
    private VehicleStatus status;
    @Column(name = "vehicle_year")
    private int year;
    private String engine;

    protected Vehicle() {}

    public Vehicle(String model, int year, String engine) {
        this.status = VehicleStatus.AVAILABLE;
        if (model == null || model.isBlank()) {
            throw new IllegalArgumentException("model must not be null");
        }
        this.model = model;
        this.year = year;
        this.engine = engine;
    }

    public Vehicle(String model, int year, String engine, String brand) {
    }

    public Long getId() {
        return id;
    }

    public String getModel() {
        return model;
    }

    public VehicleStatus getStatus() {
        return status;
    }

    public int getYear() {
        return year;
    }

    public String getEngine() {
        return engine;
    }

    public boolean isRented() {
        return this.getStatus().equals(VehicleStatus.RENTED);
    }

    public void setStatus(VehicleStatus incomingStatus) {
        Set<VehicleStatus> possibleStatus = VEHICLE_STATUS.get(this.status);

        if (incomingStatus.equals(this.status)) {
            return;
        }

        if (possibleStatus.contains(incomingStatus)) {
            this.status = incomingStatus;
        } else {
            throw new IllegalArgumentException("Validation error, possible status are: " + possibleStatus);
        }
    }

    public void addAccessory(Accessory accessory) {
    }

    public void moveForMaintenance(Maintenance maintenance) {
        this.status = VehicleStatus.UNDER_MAINTENANCE;
    }
}
