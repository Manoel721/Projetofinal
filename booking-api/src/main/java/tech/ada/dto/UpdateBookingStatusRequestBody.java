package tech.ada.dto;

import tech.ada.model.BookingStatus;

public record UpdateBookingStatusRequestBody(
        BookingStatus status
) {
}