package com.xyz.moviebooking.service;

import com.xyz.moviebooking.model.ShowEntity;
import com.xyz.moviebooking.model.TheatreEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class BrowseService {
    private final TheatreService theatreService;

    public BrowseService(TheatreService theatreService){
        this.theatreService = theatreService;
    }

    public List<ShowEntity> browseShows(String city, String movieId, String language, String genre, LocalDate date) {
        return  theatreService.getAllShows().stream()
                .filter(s -> {
                    TheatreEntity theatre = theatreService.findByTheatreId(s.getTheatreId());
                    return theatre != null && theatre.getCity().equalsIgnoreCase(city);
                })
                .filter(s -> movieId == null || movieId.isEmpty() || s.getMovieId().equalsIgnoreCase(movieId))
                .filter(show -> language == null || language.isEmpty() || show.getLanguage().equalsIgnoreCase(language))
                .filter(show -> genre == null || genre.isEmpty() || show.getGenre().equalsIgnoreCase(genre))
                .filter(show -> date == null || show.getShowDate().equals(date))
                .toList();
    }
}
