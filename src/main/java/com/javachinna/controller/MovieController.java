package com.javachinna.controller;

import com.javachinna.dto.ApiResponse;
import com.javachinna.dto.MovieDTO;
import com.javachinna.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

/**
 * REST Controller responsible for fetching movie details and storing user ratings.
 *
 * @author Chinna
 */
@RestController
@RequestMapping(path = "/api/movies")
@RequiredArgsConstructor
public class MovieController {

    private final MovieService movieService;

    /**
     * Fetches 10 top-rated movies order by box office value from omdb api
     *
     * @return {@link List} of movies
     */
    @GetMapping
    public List<MovieDTO> findTop10RatedMovies() {
        return movieService.findTop10RatedMovies();
    }

    /**
     * Finds a movie by the given title
     *
     * @param title The movie title
     * @return {@link MovieDTO}
     */
    @GetMapping("/{title}")
    public MovieDTO findMovie(@PathVariable String title) {
        return movieService.findMovieByTitle(title);
    }

    /**
     * Stores the user rating for a movie identified by its title
     *
     * @param title     The movie title
     * @param rating    The rating
     * @param principal The authenticated user principal
     * @return {@link ApiResponse}
     */
    @PostMapping("/{title}/rating/{rating}")
    public ResponseEntity<?> addRating(@PathVariable String title, @PathVariable Integer rating, Principal principal) {
        movieService.addRating(title, rating, principal);
        return ResponseEntity.ok().body(new ApiResponse(true, "success"));
    }
}
