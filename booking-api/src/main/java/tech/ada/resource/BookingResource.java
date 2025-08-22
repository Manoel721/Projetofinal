package tech.ada.resource;

import io.quarkus.security.identity.SecurityIdentity;
import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.jwt.JsonWebToken;
import tech.ada.dto.*;
import tech.ada.model.*;
import tech.ada.repository.BookingRepository;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Path("/api/v1/bookings")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@RequestScoped
@RolesAllowed({"admin"})
public class BookingResource {

    @Inject
    SecurityIdentity securityIdentity;
    @Inject
    JsonWebToken jwt;

    @Inject
    BookingRepository bookingRepository;

    @POST
    @Transactional
    @RolesAllowed({"user"})
    public Response create(BookingRequestBody request) {
        String principalName = securityIdentity.getPrincipal().getName();
        Booking booking = new Booking(principalName, request.getStartDate(), request.getEndDate());
        bookingRepository.persist(booking);

        BookingResponse response = new BookingResponse(booking);

        return Response.created(URI.create("/api/v1/bookings/" + booking.getId()))
                .entity(response)
                .build();
    }


    @GET
    @RolesAllowed({"user"})
    public Response getAll() {
        List<Booking> list = bookingRepository.listAll();
        List<BookingResponse> response = list.stream()
                .map(BookingResponse::new)
                .toList();
        return Response.ok(response).build();
    }


    @GET
    @Path("/{id}")
    @RolesAllowed({"user"})
    public Response getById(@PathParam("id") Long id) {
        Optional<Booking> booking = bookingRepository.findByIdOptional(id);
        boolean ehAdimin = securityIdentity.getRoles().contains("admin");

        if (booking.isPresent()) {
            String nome = String.valueOf(booking.get().getCustomerId());
            String principalName = securityIdentity.getPrincipal().getName();
            if (!nome.equals(principalName) && !ehAdimin) {
                return Response.status(Response.Status.FORBIDDEN)
                        .entity(Map.of("message", "Acesso não autorizado: um `user` não pode ver os agendamentos de um outro `user`."))
                        .build();
            }
        }

        if (booking.isPresent()) {
            return Response.ok(new BookingResponse(booking.get())).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(Map.of("message", "Booking with ID not found"))
                    .build();
        }
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public Response deleteById(@PathParam("id") Long id) {
        boolean deleted = bookingRepository.deleteById(id);
        return deleted ? Response.noContent().build()
                : Response.status(Response.Status.NOT_FOUND).build();
    }
}
