package tech.ada.auth;

import io.quarkus.logging.Log;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;

import javax.ws.rs.ext.Provider;

@Provider
//@Priority(Priorities.AUTHORIZATION)
//@Priority(Priorities.AUTHENTICATION)
public class LoggingFilter implements ContainerRequestFilter {

    @Override
    public void filter(ContainerRequestContext requestContext) {
        /*Log.info("Receiveing request with method %s and path %s".formatted(
                requestContext.getMethod(), requestContext.getUriInfo().getPath()));*/
    }

}