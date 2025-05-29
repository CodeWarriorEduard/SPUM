package com.example.spum_backend.service.interfaces;

import com.example.spum_backend.dto.request.BookingRequestDTO;
import com.example.spum_backend.dto.request.BookingUpdateStatusRequestDTO;
import com.example.spum_backend.dto.response.BookingResponseDTO;
import com.example.spum_backend.dto.response.FileInfoResponseDTO;

import java.util.List;


public interface BookingService {
    BookingResponseDTO createBooking(BookingRequestDTO booking);
    void updateBookingStatus(BookingUpdateStatusRequestDTO bookingUpdateStatusRequestDTO);
    FileInfoResponseDTO markABookingAsReturned(Long id);
    List<BookingResponseDTO> getAllBookingsByStudent(Long id);
}
