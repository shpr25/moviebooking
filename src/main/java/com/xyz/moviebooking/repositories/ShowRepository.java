package com.xyz.moviebooking.repositories;

import com.xyz.moviebooking.model.ShowEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ShowRepository extends JpaRepository<ShowEntity, Long> {
    public Optional<ShowEntity> findByShowId(String showId);
}

