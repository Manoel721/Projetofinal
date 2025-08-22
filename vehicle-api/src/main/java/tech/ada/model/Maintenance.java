package tech.ada.model;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "maintenances")
public class Maintenance extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String problem;

    private LocalDateTime createdAt;

    @Column(name = "vehicle_id")
    private Long vehicleId;

    public Maintenance() {
        // Construtor padrão exigido pelo JPA
    }

    public Maintenance(String problem, Long vehicleId) {
        this.problem = problem;
        this.vehicleId = vehicleId;
        this.createdAt = LocalDateTime.now();
    }

    // Getters e Setters

    public Long getId() {
        return id;
    }

    public String getProblem() {
        return problem;
    }

    public void setProblem(String problem) {
        this.problem = problem;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public Long getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(Long vehicleId) {
        this.vehicleId = vehicleId;
    }
}
