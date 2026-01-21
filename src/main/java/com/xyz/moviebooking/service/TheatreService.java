package com.xyz.moviebooking.service;

import com.xyz.moviebooking.model.ShowEntity;
import com.xyz.moviebooking.model.TheatreEntity;
import com.xyz.moviebooking.repositories.ShowRepository;
import com.xyz.moviebooking.repositories.TheatreRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TheatreService {
    private final TheatreRepository theatreRepository;
    private final ShowRepository showRepository;

    public TheatreService(TheatreRepository theatreRepository,
                          ShowRepository showRepository) {
        this.theatreRepository = theatreRepository;
        this.showRepository = showRepository;
    }

    public TheatreEntity onboardTheatre(TheatreEntity theatre) {
        return theatreRepository.save(theatre);
    }

    public ShowEntity addShow(ShowEntity show) {
        return showRepository.save(show);
    }

    public List<ShowEntity> getAllShows() {
        return showRepository.findAll();
    }

    public TheatreEntity findByTheatreId(String theatreId){
        return this.theatreRepository.findByTheatreId(theatreId);
    }
}
