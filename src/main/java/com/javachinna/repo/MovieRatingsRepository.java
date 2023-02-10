package com.javachinna.repo;

import com.javachinna.model.MovieRating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * JPA Repository for MovieRatings entity
 *
 * @author Chinna
 */
@Repository
public interface MovieRatingsRepository extends JpaRepository<MovieRating, Long> {
    Optional<MovieRating> findByMovieTitleAndUserEmail(String title, String username);
}
