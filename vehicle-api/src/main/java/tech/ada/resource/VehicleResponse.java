package tech.ada.resource;

import tech.ada.model.Vehicle;

public class VehicleResponse {

    private String model;
    private int year;
    private String engine;

    public VehicleResponse(Vehicle vehicle) {
        this.model = vehicle.getModel();
        this.year = vehicle.getYear();
        this.engine = vehicle.getEngine();
    }

    public String getModel() {
        return model;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setEngine(String engine) {
        this.engine = engine;
    }

    public String getEngine() {
        return engine;
    }

    public void setModel(String model) {
        this.model = model;
    }
}
