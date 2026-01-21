package com.xyz.moviebooking.repositories;

import com.xyz.moviebooking.model.TheatreEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TheatreRepository extends JpaRepository<TheatreEntity, Long> {
    TheatreEntity findByTheatreId(String theatreId);
}

