package com.xyz.moviebooking.service;

import com.xyz.moviebooking.model.BookingEntity;
import com.xyz.moviebooking.model.BookingStatus;
import com.xyz.moviebooking.model.ShowEntity;
import com.xyz.moviebooking.repositories.BookingRepository;
import com.xyz.moviebooking.repositories.ShowRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class BookingService {
    private final BookingRepository bookingRepository;
    private final ShowRepository showRepository;

    private final Map<String, ConcurrentHashMap<String, Boolean>> seatLocks = new ConcurrentHashMap<>();

    public BookingService(BookingRepository bookingRepository, ShowRepository showRepository) {
        this.bookingRepository = bookingRepository;
        this.showRepository = showRepository;
    }

    public BookingEntity bookTickets(String showId, List<String> seatNumbers) {
        Optional<ShowEntity> showOpt = showRepository.findByShowId(showId);
        ShowEntity show = showOpt.orElseThrow(() -> new RuntimeException("Show not found"));

        seatLocks.putIfAbsent(showId, new ConcurrentHashMap<>());
        ConcurrentHashMap<String, Boolean> locks = seatLocks.get(showId);
        for (String seat : seatNumbers) {
            locks.putIfAbsent(seat, false);
            synchronized (locks) {
                if (locks.get(seat)) {
                    throw new RuntimeException("Seat " + seat + " is already booked/locked");
                }
                locks.put(seat, true); // lock
            }
        }

        double totalPrice = 0;
        for (int i = 0; i < seatNumbers.size(); i++) {
            double price = show.getTicketPrice();
            if (i == 2) price *= 0.5; // 50% discount on 3rd ticket
            totalPrice += price;
        }

        BookingEntity booking = BookingEntity.builder()
                .bookingId(UUID.randomUUID().toString())
                .showId(showId)
                .theatreId(show.getTheatreId())
                .movieId(show.getMovieId())
                .seatNumbers(seatNumbers)
                .totalPrice(totalPrice)
                .status(BookingStatus.CONFIRMED)
                .bookingTime(LocalDateTime.now())
                .build();

        for(String seat: seatNumbers){
            locks.put(seat, false);
        }
        return booking;
    }

}
