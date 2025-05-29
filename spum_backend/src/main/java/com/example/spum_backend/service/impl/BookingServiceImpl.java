package com.example.spum_backend.service.impl;

import com.example.spum_backend.dto.request.booking.BookingCreateRequestDTO;
import com.example.spum_backend.dto.request.booking.BookingUpdateRequestDTO;
import com.example.spum_backend.dto.request.PenaltyRequestDTO;
import com.example.spum_backend.dto.response.BookingResponseDTO;
import com.example.spum_backend.entity.Booking;
import com.example.spum_backend.entity.Item;
import com.example.spum_backend.entity.Student;
import com.example.spum_backend.enumeration.BookingStatusEnum;
import com.example.spum_backend.exception.BookingConflict;
import com.example.spum_backend.exception.notFound.BookingNotFoundException;
import com.example.spum_backend.mapper.BookingMapper;
import com.example.spum_backend.repository.BookingRepository;
import com.example.spum_backend.service.interfaces.BookingService;
import com.example.spum_backend.service.interfaces.PenaltyService;
import com.example.spum_backend.service.interfaces.internal.BookingServiceEntity;
import com.example.spum_backend.service.interfaces.internal.ItemServiceEntity;
import com.example.spum_backend.service.interfaces.internal.PenaltyServiceEntity;
import com.example.spum_backend.service.interfaces.internal.StudentServiceEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class BookingServiceImpl implements BookingService, BookingServiceEntity {

    private final BookingRepository bookingRepository;
    private final ItemServiceEntity itemServiceEntity;
    private final PenaltyServiceEntity penaltyServiceEntity;
    private final StudentServiceEntity studentServiceEntity;
    private final BookingMapper bookingMapper;
    private final PenaltyService penaltyService;


    private static final ZoneId ZONE_ID = ZoneId.of("America/Bogota");
    private static final int BOOKING_DURATION_MINUTES = 10;



    private long getCurrentTimeMillis() {
        return ZonedDateTime.now(ZONE_ID).toInstant().toEpochMilli();
    }

    private void validateBookingRequest(Item item, Student student, LocalDateTime startTime) {
        if (item.getItemQuantity() <= 0) {
            throw new BookingConflict("No hay stock disponible para este artículo.");
        }

        penaltyServiceEntity.findActivePenalty(student.getUser().getId(), startTime)
                .ifPresent(p -> {
                    throw new BookingConflict("El estudiante tiene penalizaciones activas.");
                });
    }

    @Override
    public BookingResponseDTO createBooking(BookingCreateRequestDTO request) {
        Item item = itemServiceEntity.getItemById(request.getItemId());
        Student student = studentServiceEntity.findStudentByEmail(request.getEmail());

        validateBookingRequest(item, student, request.getStartTime());

        Booking booking = Booking.builder()
                .startTime(request.getStartTime())
                .endTime(request.getStartTime().plusMinutes(BOOKING_DURATION_MINUTES))
                .bookingStatus(BookingStatusEnum.IN_PROCESS)
                .student(student)
                .item(item)
                .build();

        bookingRepository.save(booking);

        item.setItemQuantity(item.getItemQuantity() - 1);
        itemServiceEntity.saveItem(item);

        return bookingMapper.toDto(booking);
    }

    @Override
    public List<BookingResponseDTO> findAllBookings() {
        return bookingRepository.findAll().stream()
                .map(bookingMapper::toDto).toList();
    }

    @Override
    public BookingResponseDTO findBookingById(Long id) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new BookingNotFoundException("Booking no encontrado con id: " + id));
        return bookingMapper.toDto(booking);
    }

    private Booking findAndPrepareBooking(Long bookingId, BookingUpdateRequestDTO request) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new BookingNotFoundException("Booking no encontrado con id: " + bookingId));

        // MapStruct actualiza solo los campos no nulos
        bookingMapper.updateEntityFromDto(request, booking);

        // Actualizar item si se especifica
        if (request.getItemId() != null) {
            Item newItem = itemServiceEntity.getItemById(request.getItemId());
            booking.setItem(newItem);
        }

        // Actualizar estudiante si se especifica
        if (request.getEmail() != null) {
            Student newStudent = studentServiceEntity.findStudentByEmail(request.getEmail());
            booking.setStudent(newStudent);
        }

        // Validar y ajustar tiempos si se especifica el startTime
        if (request.getStartTime() != null) {
            validateBookingRequest(booking.getItem(), booking.getStudent(), request.getStartTime());
            booking.setStartTime(request.getStartTime());
            booking.setEndTime(request.getStartTime().plusMinutes(BOOKING_DURATION_MINUTES));
        }

        return bookingRepository.save(booking);
    }


    @Override
    public BookingResponseDTO updateBooking(Long id, BookingUpdateRequestDTO request) {
        Booking booking = findAndPrepareBooking(id != null ? id : request.getBookingId(), request);
        return bookingMapper.toDto(booking);
    }

    @Override
    public BookingResponseDTO updateBookingStatus(BookingUpdateRequestDTO dto) {
        Booking booking = findAndPrepareBooking(dto.getBookingId(), dto);
        return bookingMapper.toDto(booking);
    }




    @Override
    public void deleteBooking(Long id) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new BookingNotFoundException("Booking no encontrado con id: " + id));

        bookingRepository.delete(booking);
    }

    @Override
    public List<BookingResponseDTO> findBookingsByStudentEmail(String email) {
        Student student = studentServiceEntity.findStudentByEmail(email);

        List<Booking> bookings = bookingRepository.findByStudentOrderByStartTimeDesc(student);

        return bookings.stream()
                .map(bookingMapper::toDto)
                .toList();
    }

    @Override
    public List<Booking> getAllBookingsSoonToEnd() {
        long now = getCurrentTimeMillis();
        int thresholdMinutes = (int) (BOOKING_DURATION_MINUTES * 0.9);

        return bookingRepository.findAll().stream()
                .filter(b -> b.getBookingStatus() == BookingStatusEnum.BOOKED)
                .filter(b -> {
                    long bookingStartMillis = b.getStartTime().plusMinutes(thresholdMinutes)
                            .atZone(ZONE_ID).toInstant().toEpochMilli();
                    return now >= bookingStartMillis;
                })
                .toList();
    }


    @Override
    public void getBookingsWithNoProcessing() {
        ZonedDateTime now = ZonedDateTime.now(ZONE_ID);
        int thresholdMinutes = (int) (BOOKING_DURATION_MINUTES * 0.5);

        bookingRepository.findAll().stream()
                .filter(b -> b.getBookingStatus() == BookingStatusEnum.IN_PROCESS)
                .filter(b -> {
                    ZonedDateTime thresholdTime = b.getStartTime()
                            .plusMinutes(thresholdMinutes)
                            .atZone(ZONE_ID);
                    return now.isAfter(thresholdTime);
                })
                .forEach(b -> updateBookingStatus(
                        new BookingUpdateRequestDTO(
                                b.getBookingId(),
                                null,
                                null,
                                null,
                                BookingStatusEnum.CANCELLED
                        )
                ));
    }


    @Override
    public void BookingsNoReturned() {
        long now = getCurrentTimeMillis();
        String description = "El estudiante no devolvió el artículo en el tiempo estipulado.";

        bookingRepository.findAll().stream()
                .filter(b -> b.getBookingStatus() == BookingStatusEnum.IN_PROCESS_OF_RETURN)
                .filter(b -> now >= getCurrentTimeMillis()) // O usar hora fija de cierre si aplica
                .forEach(b -> penaltyService.createPenalty(new PenaltyRequestDTO(
                        description, LocalDateTime.now(), 1L, b.getStudent().getUser().getEmail()
                )));
    }

    @Override
    public Booking getBookingById(Long id) {
        return bookingRepository.findById(id)
                .orElseThrow(() -> new BookingNotFoundException("Booking not found"));
    }
}



