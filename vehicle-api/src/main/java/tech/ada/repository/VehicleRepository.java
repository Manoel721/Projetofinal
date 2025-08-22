package tech.ada.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import tech.ada.model.Vehicle;

@ApplicationScoped
public class VehicleRepository implements PanacheRepository<Vehicle> {
}
