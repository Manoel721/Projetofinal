package tech.ada.dto;

import java.time.LocalDateTime;

public class MaintenanceDTO {

    private Long id;
    private String problem;
    private LocalDateTime createdAt;
    private Long vehicleId;

    public MaintenanceDTO() {
        // Construtor padrão
    }

    public MaintenanceDTO(Long id, String problem, LocalDateTime createdAt, Long vehicleId) {
        this.id = id;
        this.problem = problem;
        this.createdAt = createdAt;
        this.vehicleId = vehicleId;
    }

    // Getters e Setters

    public Long getId() {
        return id;
    }

    public String getProblem() {
        return problem;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public Long getVehicleId() {
        return vehicleId;
    }
}
