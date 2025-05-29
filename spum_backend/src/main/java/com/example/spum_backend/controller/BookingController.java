package com.example.spum_backend.controller;

import com.example.spum_backend.dto.request.BookingRequestDTO;
import com.example.spum_backend.dto.request.BookingUpdateStatusRequestDTO;
import com.example.spum_backend.dto.response.BookingResponseDTO;
import com.example.spum_backend.dto.response.FileInfoResponseDTO;
import com.example.spum_backend.entity.Booking;
import com.example.spum_backend.service.interfaces.BookingService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bookings")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping("/add")
    public BookingResponseDTO addBooking(@RequestBody BookingRequestDTO booking) {
        return bookingService.createBooking(booking);
    }

    @GetMapping("/all")
    public List<BookingResponseDTO> getAllBookings() {
        return List.of();
    }

    @PostMapping("/update-status") // Verificar
    public String updateBooking(@RequestBody BookingUpdateStatusRequestDTO booking) {
        bookingService.updateBookingStatus(booking);
        return "Booking updated to" + booking.getStatus();
    }

    @PostMapping("/mark-returned/{id}")
    public ResponseEntity<byte[]> markBookingReturned(@PathVariable Long id) {
        System.out.println("hola");
        FileInfoResponseDTO response = bookingService.markABookingAsReturned(id);

        HttpHeaders header = new HttpHeaders();
        header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename="+response.getFileName());
        header.add("Cache-Control", "no-cache, no-store, must-revalidate");
        header.add("Pragma", "no-cache");
        header.add("Expires", "0");

        return ResponseEntity.ok().headers(header).contentType(MediaType.APPLICATION_PDF).body(response.getFile());
    }

    @GetMapping("/all/{id}")
    public List<BookingResponseDTO> getAllBookingsByStudent(@PathVariable Long id) {
        return bookingService.getAllBookingsByStudent(id);
    }


}
