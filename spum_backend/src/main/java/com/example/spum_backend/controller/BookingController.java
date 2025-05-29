package com.example.spum_backend.controller;

import com.example.spum_backend.dto.request.booking.BookingCreateRequestDTO;
import com.example.spum_backend.dto.request.booking.BookingUpdateRequestDTO;
import com.example.spum_backend.dto.response.BookingResponseDTO;
import com.example.spum_backend.service.interfaces.BookingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bookings")
@CrossOrigin(origins = "http://localhost:5173")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }
    @GetMapping
    public ResponseEntity<List<BookingResponseDTO>> getAllBookings() {
        return ResponseEntity.ok(bookingService.findAllBookings());
    }
    @PostMapping
    public ResponseEntity<BookingResponseDTO> addBooking(@RequestBody BookingCreateRequestDTO bookingCreateRequestDTO) {
        BookingResponseDTO response = bookingService.createBooking(bookingCreateRequestDTO);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookingResponseDTO> updateBooking(
            @PathVariable Long id,
            @RequestBody BookingUpdateRequestDTO bookingUpdateRequestDTO) {
        BookingResponseDTO updatedBooking = bookingService.updateBooking(id, bookingUpdateRequestDTO);
        return ResponseEntity.ok(updatedBooking);
    }

    @PatchMapping("/update-status")
    public ResponseEntity<BookingResponseDTO> updateBookingStatus(@RequestBody BookingUpdateRequestDTO bookingUpdateRequestDTO) {
        BookingResponseDTO updatedBooking = bookingService.updateBookingStatus(bookingUpdateRequestDTO);
        return ResponseEntity.ok(updatedBooking);
    }

    @GetMapping("/student")
    public ResponseEntity<List<BookingResponseDTO>> findBookingsByStudentEmail(@RequestParam String email) {
        List<BookingResponseDTO> bookings = bookingService.findBookingsByStudentEmail(email);
        return ResponseEntity.ok(bookings);
    }
}
