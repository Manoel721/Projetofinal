package tech.ada.resource;


import tech.ada.model.Booking;

import java.time.Instant;
import java.time.LocalDate;

public class BookingResponse {

    private Long id;
	private String customerName;
    private LocalDate startDate;
    private LocalDate endDate;

    public BookingResponse(Booking booking) {
        this.customerName = booking.getCustomerName();
        this.startDate = booking.getStartDate();
        this.endDate = booking.getEndDate();
    }

    public String getCustomerName() {
        return customerName;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
}
