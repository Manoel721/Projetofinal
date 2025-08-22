package tech.ada.auth;

import io.quarkus.panache.common.Parameters;
import io.smallrye.jwt.build.Jwt;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.jwt.Claims;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@Path("/api/v1/auth")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UsuarioResource {
    
    @POST
    @Path("/sign-up")
    @Transactional
    public Response cadastro (CriaUsuarioDTO dto){
        String senhaCriptografada = BCryptAdapter.encrypt(dto.senha());
        Usuario usuario = new Usuario(dto.usuario(), senhaCriptografada, dto.email());
        usuario.persist();
        return Response.status(201).build();
    }

    @POST
    @Path("/sign-in")
    @Transactional
    public Response login (LoginDTO dto){

        Optional<Usuario> possivelUsuario = Usuario.find("email = :email", Parameters.with("email", dto.email()))
                .firstResultOptional();

        if (possivelUsuario.isEmpty()) {
            return Response.status(Response.Status.FORBIDDEN).build();
        }

        Usuario usuario = possivelUsuario.get();

        Boolean senhaIgual = BCryptAdapter.checkPassword(dto.senha(), usuario.getSenha());


        if (senhaIgual) {
            String tokenJwt  = Jwt.claims()
                    .issuer("https://tech.ada/alou")
                    .expiresAt(Instant.now().plus(50, ChronoUnit.MINUTES))
                    .groups(Set.of(usuario.getPapel()))
                    .claim(Claims.upn, usuario.getId())
                    .sign();
            return Response.ok(Map.of("token", tokenJwt)).build();
        }
        return Response.status(Response.Status.FORBIDDEN).build();
    }
    public record LoginDTO (String email, String senha) {}
    public record CriaUsuarioDTO (String usuario, String senha, String email) {}
}
