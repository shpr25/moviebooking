package com.xyz.moviebooking.controller;

import com.xyz.moviebooking.model.ShowEntity;
import com.xyz.moviebooking.model.TheatreEntity;
import com.xyz.moviebooking.service.TheatreService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/theatres")
public class TheatreController {
    private final TheatreService theatreService;

    public TheatreController(TheatreService theatreService) {
        this.theatreService = theatreService;
    }

    @PostMapping
    public TheatreEntity onboard(@RequestBody TheatreEntity theatre){
        return theatreService.onboardTheatre(theatre);
    }

    @PostMapping("/shows")
    public void addShow(@RequestBody ShowEntity show) {
        theatreService.addShow(show);
    }
}
