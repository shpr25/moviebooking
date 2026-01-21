package com.xyz.moviebooking.controller;

import com.xyz.moviebooking.model.BookingEntity;
import com.xyz.moviebooking.service.BookingService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/booking")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping("/book")
    public BookingEntity book(@RequestParam String showId,
                              @RequestBody List<String> seatNumbers) throws Exception {
        return bookingService.bookTickets(showId, seatNumbers);
    }
}
