package tech.ada.dto;

import tech.ada.model.Booking;
import tech.ada.model.BookingStatus;

import java.time.LocalDate;


public record BookingResponseBody(
        Long id,
        String customerName,
        LocalDate startDate,
        LocalDate endDate,
        BookingStatus status
) {

    public BookingResponseBody(Booking b1) {

        this(b1.getId(), b1.getCustomerName(), b1.getStartDate(), b1.getEndDate(), b1.getStatus());
    }
}