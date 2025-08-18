package tech.ada.model;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Entity
public class Booking extends PanacheEntityBase {

    private static final Map<BookingStatus, Set<BookingStatus>> BOOKING_STATUS = new HashMap<>() {
    };

    static {
        BOOKING_STATUS.put(BookingStatus.CREATED, Set.of(BookingStatus.CANCELED, BookingStatus.FINISHED));
        BOOKING_STATUS.put(BookingStatus.CANCELED, Set.of(BookingStatus.CREATED, BookingStatus.FINISHED));
        BOOKING_STATUS.put(BookingStatus.FINISHED, Set.of(BookingStatus.CREATED));
    }

    @Id
    @GeneratedValue
	private Long id;
    private Long vehicleId;
    private String customerName;
    private LocalDate startDate;
    private LocalDate endDate;
    private BookingStatus status;

    protected Booking() {}

    public Booking(String customerName, LocalDate startDate, LocalDate endDate) {
        this.status = BookingStatus.CREATED;
        if (customerName == null || customerName.isBlank()) {
            throw new IllegalArgumentException("customerName must not be null");
        }
        this.customerName = customerName;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Booking(Long customerName, String startDate, LocalDate endDate, LocalDate brand) {
    }

    public Long getId() {
        return id;
    }

    public String getCustomerName() {
        return customerName;
    }

    public BookingStatus getStatus() {
        return status;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public boolean isCanceled() {
        return this.getStatus().equals(BookingStatus.CANCELED);
    }

    public void setStatus(BookingStatus incomingStatus) {
        Set<BookingStatus> possibleStatus = BOOKING_STATUS.get(this.status);

        if (incomingStatus.equals(this.status)) {
            return;
        }

        if (possibleStatus.contains(incomingStatus)) {
            this.status = incomingStatus;
        } else {
            throw new IllegalArgumentException("Validation error, possible status are: " + possibleStatus);
        }
    }


}
