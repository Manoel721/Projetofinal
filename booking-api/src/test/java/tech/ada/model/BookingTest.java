package tech.ada.model;

import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import tech.ada.dto.BookingRequestBody;
import tech.ada.resource.BookingResource;

import java.time.LocalDate;

public class BookingTest {


    Long id;
    Long vehicleId;
    String customerName;
    LocalDate startDate;
    LocalDate endDate;
    BookingStatus status;

    @Test
    void shouldCreateBookingWithValidDatesAndAvailable() {
        Booking b1 = new Booking(1L, "Adriano", LocalDate.parse("2025-10-10"), LocalDate.parse("2025-12-12"));

        // assertions
        Assertions.assertNotNull(b1.getStartDate());
        Assertions.assertNotNull(b1.getEndDate());
        Assertions.assertEquals(BookingStatus.CREATED, b1.getStatus());
    }

    @Test
    void shouldNotCreateBookingWithStartDateInThePast() {

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new Booking(1L, "Adriano", LocalDate.parse("2024-10-10"), LocalDate.parse("2025-12-12"));
        });

    }

    @Test
    void shouldNotCreateBookingWithEndDateBeforeStartDate() {
        // assertions
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new Booking(1L, "Adriano", LocalDate.parse("2025-10-10"), LocalDate.parse("2020-12-12"));
        });
    }

    @Test
    void shouldNotCreateBookingOnUnavailableCar() {

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            Booking b1 = new Booking(1L, "Adriano", LocalDate.parse("2025-10-10"), LocalDate.parse("2025-12-12"));
            b1.setStatus(BookingStatus.CANCELED);
        });
    }

    @Test
    void shouldCancelBookingWhenStatusIsCreated() {

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new Booking(1L, "Adriano", LocalDate.parse("2025-10-10"), LocalDate.parse("2025-12-12"));
        });
    }

    @Test
    void shouldCancelBookingWhenStatusIsCancelledOrFinished() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new Booking(1L, "Adriano", LocalDate.parse("2025-10-10"), LocalDate.parse("2025-12-12"));
        });
    }

    @Test
    void shouldFinishBookingWhenStatusIsCreated() {

        Booking b1 =
        new Booking(1L, "Adriano", LocalDate.parse("2025-10-10"), LocalDate.parse("2025-12-12"));
        b1.setStatus(BookingStatus.FINISHED);
        Assertions.assertEquals(BookingStatus.FINISHED, b1.getStatus());
    }

    @Test
    void shouldFinishBookingWhenStatusIsCancelledOrFinished() {

        Booking b1 =
                new Booking(1L, "Adriano", LocalDate.parse("2025-10-10"), LocalDate.parse("2025-12-12"));
        b1.setStatus(BookingStatus.FINISHED);
        Assertions.assertEquals(BookingStatus.FINISHED, b1.getStatus());
    }

}
