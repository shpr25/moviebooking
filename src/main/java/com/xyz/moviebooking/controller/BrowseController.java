package com.xyz.moviebooking.controller;

import com.xyz.moviebooking.model.ShowEntity;
import com.xyz.moviebooking.service.BrowseService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/browse")
public class BrowseController {
    private final BrowseService browseService;

    public BrowseController(BrowseService browseService) {
        this.browseService = browseService;
    }

    @GetMapping("/shows")
    public List<ShowEntity> browse(
            @RequestParam String city,
            @RequestParam(required = false) String movieId,
            @RequestParam(required = false) String language,
            @RequestParam(required = false) String genre,
            @RequestParam(required = false) String date
    ){

        LocalDate showDate = date != null ? LocalDate.parse(date) : null;
        return browseService.browseShows(city, movieId, language, genre, showDate);
    }
}
