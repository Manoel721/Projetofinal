package tech.ada.resource;

import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import tech.ada.dto.*;
import tech.ada.model.*;
import tech.ada.repository.MaintenanceRepository;
import tech.ada.repository.VehicleRepository;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Path("/api/v1/vehicles")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@RequestScoped
@PermitAll
public class VehicleResource {

    //@Inject
    //VehicleService vehicleService;

    @Inject
    VehicleRepository vehicleRepository;

    @Inject
    MaintenanceRepository maintenanceRepository;



    @POST
    @Transactional
    public Response create(VehicleRequestBody request) {
        Vehicle vehicle = new Vehicle(request.model(), request.year(), request.engine());
        //Vehicle vehicle = new Vehicle("Uno", 2017, "1.4");
        vehicleRepository.persist(vehicle);

        VehicleResponse response = new VehicleResponse(vehicle);
        return Response.created(URI.create("/api/v1/vehicles/" + vehicle.id))
                .entity(response)
                .build();
    }


    @GET
    @RolesAllowed({"admin"})
    public Response getAll() {
        List<Vehicle> list = vehicleRepository.listAll();
        List<VehicleResponse> response = list.stream()
                .map(VehicleResponse::new)
                .toList();
        return Response.ok(response).build();
    }


    @GET
    @Path("/{id}")
    public Response getById(@PathParam("id") Long id) {
        Optional<Vehicle> vehicle = vehicleRepository.findByIdOptional(id);
        if (vehicle.isPresent()) {
            return Response.ok(new VehicleResponse(vehicle.get())).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(Map.of("message", "Vehicle with ID not found"))
                    .build();
        }
    }


    @POST
    @Path("/{vehicleId}/maintenances")
    @Transactional
    public Response addMaintenance(@PathParam("vehicleId") Long vehicleId, CreateMaintenanceRecord request) {
        Optional<Vehicle> possibleVehicle = vehicleRepository.findByIdOptional(vehicleId);
        if (possibleVehicle.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        Vehicle vehicle = possibleVehicle.get();
        Maintenance maintenance = new Maintenance(request.problem(), vehicleId);
        maintenanceRepository.persist(maintenance);

        vehicle.moveForMaintenance(maintenance);
        vehicleRepository.persist(vehicle);

        return Response.created(URI.create("/api/v1/vehicles/%d/maintenances/%d"
                .formatted(vehicleId, maintenance.getId()))).build();
    }



    @DELETE
    @Path("/{id}")
    @Transactional
    public Response deleteById(@PathParam("id") Long id) {
        boolean deleted = vehicleRepository.deleteById(id);
        return deleted ? Response.noContent().build()
                : Response.status(Response.Status.NOT_FOUND).build();
    }



}
