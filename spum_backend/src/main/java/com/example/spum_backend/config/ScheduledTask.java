package com.example.spum_backend.config;

import com.example.spum_backend.entity.Booking;
import com.example.spum_backend.service.interfaces.internal.BookingServiceEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ScheduledTask {

//    private final BookingServiceEntity bookingService;
//    private final JavaMailSender mailSender;
//
//    public ScheduledTask(BookingServiceEntity bookingService, JavaMailSender mailSender) {
//        this.bookingService = bookingService;
//        this.mailSender = mailSender;
//    }
//
//
//
//    @Scheduled(cron = "30 */5 * * * *")
//    public void handleBookingsSoonToEnd() {
//        List<Booking> bookingToEnd = bookingService.getAllBookingsSoonToEnd();
//        // Send email
//
//        String defaultMessage = "Querido usuario queremos recordarle que su reserva está a punto de vencer";
//        String subject = "Reserva a punto de expirar";
//        for (Booking booking : bookingToEnd) {
//            SimpleMailMessage mailMessage = new SimpleMailMessage();
//            mailMessage.setSubject(subject);
//            mailMessage.setText(defaultMessage);
//            mailMessage.setTo(booking.getStudent().getUser().getUsername());
//            mailSender.send(mailMessage);
//        }
//    }
//
//    @Scheduled(cron = "0 */1 * * * *")
//    public void handleBookingsWithNoProcessing() {
//        bookingService.getBookingsWithNoProcessing();
//    }
//
//    @Scheduled(cron = "0 0 18 * * *")
//    public void handleNoReturnedArticles() {
//        bookingService.BookingsNoReturned();
//    }

}
