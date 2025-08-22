package tech.ada.resource;

import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import tech.ada.dto.BookingRequestBody;

import java.time.LocalDate;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@QuarkusTest
class BookingResourceTest {

    @Test
    @TestSecurity(user = "user1", roles = {"user"})
    void deveCriarBookingComSucesso() {
        BookingRequestBody request = new BookingRequestBody();
        request.setStartDate(LocalDate.now().plusDays(1));
        request.setEndDate(LocalDate.now().plusDays(3));

        given()
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post("/api/v1/bookings")
                .then()
                .statusCode(201)
                .body("id", notNullValue())
                .body("customerId", equalTo("user1"));
    }

    @Test
    @TestSecurity(user = "user1", roles = {"user"})
    void deveListarTodosOsBookings() {
        given()
                .accept(ContentType.JSON)
                .when()
                .get("/api/v1/bookings")
                .then()
                .statusCode(200)
                .body("$", is(not(empty())));
    }

    @Test
    @TestSecurity(user = "user1", roles = {"user"})
    void deveBuscarBookingPorId() {
        // Primeiro cria um booking
        Long id = given()
                .contentType(ContentType.JSON)
                .body(new BookingRequestBody())
                .when()
                .post("/api/v1/bookings")
                .then()
                .statusCode(201)
                .extract()
                .path("id");

        // Depois busca pelo ID
        given()
                .accept(ContentType.JSON)
                .when()
                .get("/api/v1/bookings/" + id)
                .then()
                .statusCode(200)
                .body("id", equalTo(id));
    }

    @Test
    @TestSecurity(user = "user1", roles = {"user"})
    void deveRetornar403AoBuscarBookingDeOutroUsuario() {
        // Cria booking como user2
        Long id = given()
                .contentType(ContentType.JSON)
                .body(new BookingRequestBody())
                .header("X-User", "user2") // Simula outro usuário
                .when()
                .post("/api/v1/bookings")
                .then()
                .statusCode(201)
                .extract()
                .path("id");

        // Tenta buscar como user1
        given()
                .accept(ContentType.JSON)
                .when()
                .get("/api/v1/bookings/" + id)
                .then()
                .statusCode(403);
    }

    @Test
    @TestSecurity(user = "admin", roles = {"admin"})
    void deveDeletarBookingComoAdmin() {
        // Cria booking
        Long id = given()
                .contentType(ContentType.JSON)
                .body(new BookingRequestBody())
                .when()
                .post("/api/v1/bookings")
                .then()
                .statusCode(201)
                .extract()
                .path("id");

        // Deleta como admin
        given()
                .when()
                .delete("/api/v1/bookings/" + id)
                .then()
                .statusCode(204);
    }
}
