package tech.ada.dto;

import tech.ada.model.VehicleStatus;

public class VehicleRequestBody {

    public String model;
    private VehicleStatus status;
    private int year;
    private String engine;

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public VehicleStatus getStatus() {
        return status;
    }

    public void setStatus(VehicleStatus status) {
        this.status = status;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getEngine() {
        return engine;
    }

    public void setEngine(String engine) {
        this.engine = engine;
    }

    public String model() {
        return model;
    }

    public int year() {
        return year;
    }

    public String engine() {
        return engine;
    }
}
