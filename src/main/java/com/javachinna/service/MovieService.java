package com.javachinna.service;

import com.javachinna.dto.MovieDTO;

import java.security.Principal;
import java.util.List;

/**
 * Service interface that provides movie related functions
 *
 * @author Chinna
 */
public interface MovieService {
    /**
     * Finds a movie by the given title
     *
     * @param title The movie title
     * @return {@link MovieDTO}
     */
    MovieDTO findMovieByTitle(String title);

    /**
     * Fetches 10 top-rated movies order by box office value from omdb api
     *
     * @return {@link List} of movies
     */
    List<MovieDTO> findTop10RatedMovies();

    /**
     * Stores the user rating for a movie identified by its title
     *
     * @param title     The movie title
     * @param rating    The rating
     * @param principal The authenticated user principal
     */
    void addRating(String title, Integer rating, Principal principal);
}
