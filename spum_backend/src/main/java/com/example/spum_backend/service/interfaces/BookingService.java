package com.example.spum_backend.service.interfaces;

import com.example.spum_backend.dto.request.booking.BookingCreateRequestDTO;
import com.example.spum_backend.dto.request.booking.BookingUpdateRequestDTO;
import com.example.spum_backend.dto.response.BookingResponseDTO;

import java.util.List;


public interface BookingService {
    BookingResponseDTO createBooking(BookingCreateRequestDTO booking);
    List<BookingResponseDTO> findAllBookings();
    BookingResponseDTO findBookingById(Long id);
    BookingResponseDTO updateBooking(Long id, BookingUpdateRequestDTO booking);
    BookingResponseDTO updateBookingStatus(BookingUpdateRequestDTO bookingUpdateStatusRequestDTO);
    void deleteBooking(Long id);

    // Opcional: métodos de negocio específicos, p.ej.:
    List<BookingResponseDTO> findBookingsByStudentEmail(String email);
}
