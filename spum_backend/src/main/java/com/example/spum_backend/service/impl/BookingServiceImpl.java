package com.example.spum_backend.service.impl;

import com.example.spum_backend.dto.request.BookingRequestDTO;
import com.example.spum_backend.dto.request.BookingUpdateStatusRequestDTO;
import com.example.spum_backend.dto.request.PenaltyRequestDTO;
import com.example.spum_backend.dto.response.BookingResponseDTO;
import com.example.spum_backend.dto.response.FileInfoResponseDTO;
import com.example.spum_backend.entity.Booking;
import com.example.spum_backend.entity.Item;
import com.example.spum_backend.entity.Penalty;
import com.example.spum_backend.entity.Student;
import com.example.spum_backend.enumeration.BookingStatusEnum;
import com.example.spum_backend.exception.BookingConflict;
import com.example.spum_backend.exception.BookingNotFoundException;
import com.example.spum_backend.repository.BookingRepository;
import com.example.spum_backend.service.InvoiceService;
import com.example.spum_backend.service.interfaces.BookingService;
import com.example.spum_backend.service.interfaces.PenaltyService;
import com.example.spum_backend.service.interfaces.internal.BookingServiceEntity;
import com.example.spum_backend.service.interfaces.internal.ItemServiceEntity;
import com.example.spum_backend.service.interfaces.internal.PenaltyServiceEntity;
import com.example.spum_backend.service.interfaces.internal.StudentServiceEntity;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class BookingServiceImpl implements BookingService, BookingServiceEntity {

    private final BookingRepository bookingRepository;
    private final ItemServiceEntity itemServiceEntity;
    private final PenaltyServiceEntity penaltyServiceEntity;
    private final StudentServiceEntity studentServiceEntity;
    private final ModelMapper modelMapper;
    private final PenaltyService penaltyService;
    private final InvoiceService invoiceService; // Fix and use an interface

    public BookingServiceImpl(BookingRepository bookingRepository, ItemServiceEntity itemServiceEntity, PenaltyServiceEntity penaltyServiceEntity, StudentServiceEntity studentServiceEntity, ModelMapper modelMapper, PenaltyService penaltyService, InvoiceService invoiceService) {
        this.bookingRepository = bookingRepository;
        this.itemServiceEntity = itemServiceEntity;
        this.penaltyServiceEntity = penaltyServiceEntity;
        this.studentServiceEntity = studentServiceEntity;
        this.modelMapper = modelMapper;
        this.penaltyService = penaltyService;
        this.invoiceService = invoiceService;
    }

    //
    private long getClosingHour(){
        ZoneId zoneId = ZoneId.of("America/Bogota");
        ZonedDateTime zonedDateTime = ZonedDateTime.now(zoneId);
        return zonedDateTime.toInstant().toEpochMilli();
    }


    @Override
    public BookingResponseDTO createBooking(BookingRequestDTO booking) {

        Item itemToBook = itemServiceEntity.getItemById(booking.getItemId());

        Student student = studentServiceEntity.findStudentByEmail(booking.getEmail());
        // Look for active penalties
        Optional<Penalty> penalty = penaltyServiceEntity.findActivePenalty(student.getUser().getUserId(), booking.getStartTime());

        if(penalty.isPresent() ) {
            throw new BookingConflict("User can not book an item because there are active penalties");
        }

        if(itemToBook.getItemQuantity() <= 0){
            throw new BookingConflict("Can not book because there is no stock for this item");
        }

        // Look if user exists



        // Creating booking

        Booking booking1 = Booking
                .builder()
                .startTime(booking.getStartTime())
                .endTime(booking.getStartTime().plusMinutes(10L))
                .bookingStatus(BookingStatusEnum.IN_PROCESS)
                .student(student)
                .item(itemToBook)
                .build();

        bookingRepository.save(booking1);

        // Update item quantity
        itemToBook.setItemQuantity(itemToBook.getItemQuantity() - 1);
        itemServiceEntity.saveItem(itemToBook);

        return modelMapper.map(booking1, BookingResponseDTO.class);
    }

    @Override
    public Booking getBookingById(Long id) {
        return bookingRepository.findById(id)
                .orElseThrow(() -> new BookingNotFoundException("Booking not found"));
    }

    @Override
    public void updateBookingStatus(BookingUpdateStatusRequestDTO bookingUpdateStatusRequestDTO) {
        Booking bookingToUpdate = getBookingById(bookingUpdateStatusRequestDTO.getBookingId());
        bookingToUpdate.setBookingStatus(bookingUpdateStatusRequestDTO.getStatus());
        bookingRepository.save(bookingToUpdate);
    }



    @Override
    public List<Booking> getAllBookingsSoonToEnd() {
        // Verify
        List<Booking> bookings = bookingRepository.findAll();
        List<Booking> bookingsAboutToEnd = new ArrayList<>();

        // Convert it to millis
        long timeNowMillis = System.currentTimeMillis();

        int numberOfMinutes = (int) (10 * 0.9 );

        for (Booking booking : bookings) {
            // To the start date the 90%
            LocalDateTime startTime = booking.getStartTime().plusMinutes(numberOfMinutes);
            ZonedDateTime zdt = startTime.atZone(ZoneId.of("America/Bogota"));

            long time = zdt.toInstant().toEpochMilli();

            if((timeNowMillis >= time) && booking.getBookingStatus() == BookingStatusEnum.BOOKED){ // Change for booked
                // Time almost over or over by now
                bookingsAboutToEnd.add(booking);
            }
        }

        return bookingsAboutToEnd;
    }

    @Override
    public void getBookingsWithNoProcessing() {
        // Verify
        List<Booking> bookings = bookingRepository.findAll();

        // Convert it to millis
        long timeNowMillis = System.currentTimeMillis();

        int numberOfMinutes = (int) (10 * 0.5 );

        System.out.println(numberOfMinutes);
        for (Booking booking : bookings) {
            // To the start date the 90%
            LocalDateTime startTime = booking.getStartTime().plusMinutes(numberOfMinutes);
            ZonedDateTime zdt = startTime.atZone(ZoneId.of("America/Bogota"));

            long time = zdt.toInstant().toEpochMilli();

            if((timeNowMillis >= time) && booking.getBookingStatus() == BookingStatusEnum.IN_PROCESS){ // Change for booked
                // Time almost over or over by now
                updateBookingStatus(new BookingUpdateStatusRequestDTO(BookingStatusEnum.CANCELLED, booking.getBookingId()));
            }
        }

    }

    @Override
    public void BookingsNoReturned() {
        List<Booking> bookings = bookingRepository.findAll();
        String description = "El estudiante no devolvió el articulo en el tiempo estipulado";
        for (Booking booking : bookings) {
            if(booking.getBookingStatus() == BookingStatusEnum.IN_PROCESS_OF_RETURN && System.currentTimeMillis() >= getClosingHour()){
                penaltyService.createPenalty(new PenaltyRequestDTO(description, LocalDateTime.now(), 1L,booking.getStudent().getUser().getEmail()));
            }
        }
    }


    @Override
    public FileInfoResponseDTO markABookingAsReturned(Long id) {
        Booking booking = getBookingById(id);
        updateBookingStatus(new BookingUpdateStatusRequestDTO(BookingStatusEnum.RETURNED, booking.getBookingId()));
        return invoiceService.getInvoiceFile(booking);
    }


    @Override
    public List<BookingResponseDTO> getAllBookingsByStudent(Long id) {
        System.out.println(bookingRepository.findAllByStudent_StudentId(id));
        return bookingRepository.findAllByStudent_StudentId(id)
                .stream().map((booking)-> modelMapper.map(booking, BookingResponseDTO.class)).collect(Collectors.toList());
    }
}



