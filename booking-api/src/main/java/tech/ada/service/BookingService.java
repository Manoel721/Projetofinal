package tech.ada.service;

import io.quarkus.security.Authenticated;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.LockModeType;
import jakarta.transaction.TransactionScoped;
import jakarta.transaction.Transactional;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import tech.ada.model.Booking;
import tech.ada.repository.BookingDAO;

@ApplicationScoped
public class BookingService {
    @Inject
    BookingDAO bookingDAO;

    @Id
    @GeneratedValue
    private Long id;


    public BookingService(BookingDAO bookingDAO) {
        this.bookingDAO = bookingDAO;
    }

    @Transactional
    public void createBooking(Booking booking){
        this.bookingDAO.persist(booking);
    }


    public Booking getBookId (Long id){
        Booking byId = this.bookingDAO.findById(id, LockModeType.PESSIMISTIC_WRITE);
        return byId;
    }
}
