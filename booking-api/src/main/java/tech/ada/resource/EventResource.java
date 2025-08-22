package tech.ada.resource;

import io.quarkus.logging.Log;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;


@Path("/events")
    public class EventResource {
        @Channel("booking-created")
        Emitter<String> bookingCreatedEmitter;

            @POST
            @Consumes("text/plain")
            public Response createEvent (String event) {
            bookingCreatedEmitter.send(event);
            Log.info("Message sent");
            return Response.status(Response.Status.OK).build();

    }
}
