package tech.ada.service;

import io.quarkus.logging.Log;
import io.smallrye.reactive.messaging.kafka.api.IncomingKafkaRecordMetadata;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.LockModeType;
import jakarta.transaction.Transactional;
import org.eclipse.microprofile.reactive.messaging.Acknowledgment;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Message;
import tech.ada.model.Booking;
import tech.ada.repository.BookingDAO;

import java.util.Optional;
import java.util.concurrent.CompletionStage;

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
        return this.bookingDAO.findById(id, LockModeType.PESSIMISTIC_WRITE);
    }

       /*return message.nack(new RuntimeException( "Deu muito ruim aqui na hora de processar a mensage"));
        @Incoming("booking-with-fail")
        @Acknowledgment(Acknowledgment.Strategy.POST_PROCESSING)
            Log. info("DLQ: " + message) ;*/

    @Incoming("receive-booking-created")
    public CompletionStage<Void> consume (Message<String> message) {
        Log.info("message received: " + message.getPayload());
        Optional<IncomingKafkaRecordMetadata> metadata = message.getMetadata(IncomingKafkaRecordMetadata.class);
        if (metadata.isPresent()) {
            IncomingKafkaRecordMetadata incomingKafkaRecordMetadata = metadata.get();
            Log.info("Consumindo mensagem de offset: "+ incomingKafkaRecordMetadata.getOffset());
            Log.info("Consumindo mensagem de partição: "+ incomingKafkaRecordMetadata.getPartition());
        }
        return message.ack();
    }

    @Incoming("booking-with-fail")
    @Acknowledgment(Acknowledgment.Strategy.POST_PROCESSING)
    public void consume(String message) {
        Log.info("DLQ: "+message);
    }
}
