package tech.ada.resource;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import tech.ada.dto.MaintenanceDTO;
import tech.ada.dto.VehicleRequestBody;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@QuarkusTest
class VehicleResourceIT {

    @Test
    void deveCriarUmVeiculoComSucesso() {
        VehicleRequestBody request = new VehicleRequestBody("Fusca", 1980, "1.6");

        given()
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post("/api/v1/vehicles")
                .then()
                .statusCode(201)
                .body("model", equalTo("Fusca"))
                .body("year", equalTo(1980))
                .body("engine", equalTo("1.6"));
    }

    @Test
    void deveBuscarVeiculoPorId() {
        // Primeiro cria
        Long id = given()
                .contentType(ContentType.JSON)
                .body(new VehicleRequestBody("Gol", 2005, "1.0"))
                .when()
                .post("/api/v1/vehicles")
                .then()
                .statusCode(201)
                .extract()
                .path("id");

        // Depois busca
        given()
                .when()
                .get("/api/v1/vehicles/" + id)
                .then()
                .statusCode(200)
                .body("model", equalTo("Gol"));
    }

    @Test
    void deveRetornar404AoBuscarVeiculoInexistente() {
        given()
                .when()
                .get("/api/v1/vehicles/99999")
                .then()
                .statusCode(404)
                .body("message", equalTo("Vehicle with ID not found"));
    }

    @Test
    void deveAdicionarManutencaoAoVeiculo() {
        Long id = given()
                .contentType(ContentType.JSON)
                .body(new VehicleRequestBody("Palio", 2010, "1.4"))
                .when()
                .post("/api/v1/vehicles")
                .then()
                .statusCode(201)
                .extract()
                .path("id");

        MaintenanceDTO maintenance = new MaintenanceDTO("Troca de óleo");

        given()
                .contentType(ContentType.JSON)
                .body(maintenance)
                .when()
                .post("/api/v1/vehicles/" + id + "/maintenances")
                .then()
                .statusCode(201)
                .header("Location", containsString("/api/v1/vehicles/" + id + "/maintenances/"));
    }

    @Test
    void deveDeletarVeiculoPorId() {
        Long id = given()
                .contentType(ContentType.JSON)
                .body(new VehicleRequestBody("Celta", 2008, "1.0"))
                .when()
                .post("/api/v1/vehicles")
                .then()
                .statusCode(201)
                .extract()
                .path("id");

        given()
                .when()
                .delete("/api/v1/vehicles/" + id)
                .then()
                .statusCode(204);
    }

    @Test
    void deveRetornar404AoDeletarVeiculoInexistente() {
        given()
                .when()
                .delete("/api/v1/vehicles/99999")
                .then()
                .statusCode(404);
    }
}
