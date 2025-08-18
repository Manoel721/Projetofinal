package tech.ada.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import tech.ada.model.Maintenance;

@ApplicationScoped
public class MaintenanceRepository implements PanacheRepository<Maintenance> {
}
