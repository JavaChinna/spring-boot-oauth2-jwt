package com.javachinna.service;

import com.javachinna.dto.MovieDTO;
import com.javachinna.model.Movie;

/**
 * Service interface that provides OMDB API connection
 *
 * @author Chinna
 */
public interface OMDBService {
    /**
     * Fetches the box office value by movie title from OMDB API
     *
     * @param movie The {@link Movie}
     * @return MovieDTO
     */
    MovieDTO enrichMovieWithBoxOfficeValue(Movie movie);
}
