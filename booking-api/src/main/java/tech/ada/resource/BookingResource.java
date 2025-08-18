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
@PermitAll
public class BookingResource {

    //@Inject
    //BookingService bookingService;

    @Inject
    BookingRepository bookingRepository;

    @POST
    @Transactional
    public Response create(BookingRequestBody request) {
        Booking booking = new Booking(request.getCustomerName(), request.getStartDate(), request.getEndDate());
        //Booking booking = new Booking("Daniel", LocalDate.now(), LocalDate.now().plusDays(5));
        bookingRepository.persist(booking);

        BookingResponse response = new BookingResponse(booking);
        return Response.created(URI.create("/api/v1/bookings/" + booking.getId()))
                .entity(response)
                .build();
    }


    @GET
    @RolesAllowed({"admin"})
    public Response getAll() {
        List<Booking> list = bookingRepository.listAll();
        List<BookingResponse> response = list.stream()
                .map(BookingResponse::new)
                .toList();
        return Response.ok(response).build();
    }


    @GET
    @Path("/{id}")
    public Response getById(@PathParam("id") Long id) {
        Optional<Booking> booking = bookingRepository.findByIdOptional(id);
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
