package tech.ada.resource;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.quarkus.logging.Log;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import jakarta.transaction.Transactional;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tech.ada.dto.BookingRequestBody;
import tech.ada.model.Booking;
import tech.ada.model.BookingStatus;

import java.time.LocalDate;

@QuarkusTest
public class BookingResourceIntegrationTest {

    //static ObjectMapper objectMapper = new ObjectMapper();
    static ObjectMapper objectMapper = JsonMapper.builder()
            .addModule(new JavaTimeModule())
            .build();

    private String createBooking() throws JsonProcessingException {
        BookingRequestBody BookingRequestBody = new BookingRequestBody();
        BookingRequestBody.setStatus(BookingStatus.CANCELED);
        BookingRequestBody.setCustomerName("José");
        BookingRequestBody.setStartDate(LocalDate.parse("2025-10-10"));
        BookingRequestBody.setEndDate(LocalDate.parse("2025-12-12"));

        String body = objectMapper.writeValueAsString(BookingRequestBody);

        Response response = RestAssured.given()
                .contentType("application/json")
                .body(body)
                .post("api/v1/vehicles/bookings")
                .thenReturn();

        return response.getHeader("Location");
    }

    @Transactional
    public void createBookingInDatabase() {
        Booking b1 = new Booking(1L, "Adriano", LocalDate.parse("2025-10-10"), LocalDate.parse("2025-12-12"));
        b1.persist();
    }

    @BeforeEach
    void beforeEach() {
        Log.info("Executando antes de todos os testes");
    }

    @Test
    void shouldReturn201WhenSendAValidBooking() {

        RestAssured.given()
                .contentType("application/json")
                .body("""
                       {
                         "customerName": "Marcos",
                         "startDate": "2025-10-10",
                         "endDate": "2025-12-12"              
                       }
                      """)
                .post("api/v1/vehicles/bookings")
                .then()
                .statusCode(201);
    }

    @Test
    void shouldReturn400WhenSendInvalidDate() {

        RestAssured.given()
                .contentType("application/json")
                .body("""
                       {
                         "customerName": "Marcos",
                         "startDate": "2025-40-10",
                         "endDate": "2025-12-12"              
                       }
                      """)
                .post("api/v1/vehicles/bookings")
                .then()
                .statusCode(400);
    }

    @Test
    void shouldReturn201WhenCancelBooking() {

        RestAssured.given()
                .contentType("application/json")
                .body("""
                       {
                         "customerName": "Marcos",
                         "startDate": "2025-10-10",
                         "endDate": "2025-12-12",
                          "status": "CANCELED"                                                             
                       }
                      """)
                .patch("api/v1/vehicles/bookings/1")
                .then()
                .statusCode(204);
    }


    @Test
    void shouldNotCancelBookingWhenAlreadyCancelled() {
        RestAssured.given()
                .contentType("application/json")
                .body("""
                       {
                         "customerName": "Marcos",
                         "startDate": "2025-10-10",
                         "endDate": "2025-12-12",
                          "status": "CANCELED"                                                             
                       }
                      """)
                .patch("api/v1/vehicles/bookings/1")
                .then()
                .statusCode(204);
    }

    @Test
    void shouldFinishBooking() {
        RestAssured.given()
                .contentType("application/json")
                .body("""
                       {
                         "customerName": "Marcos",
                         "startDate": "2025-10-10",
                         "endDate": "2025-12-12",
                          "status": "FINISHED"                                                             
                       }
                      """)
                .patch("api/v1/vehicles/bookings/1")
                .then()
                .statusCode(204);
    }

}
