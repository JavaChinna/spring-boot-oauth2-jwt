package com.javachinna.service.impl;

import com.javachinna.dto.MovieDTO;
import com.javachinna.exception.ResourceNotFoundException;
import com.javachinna.model.Movie;
import com.javachinna.model.MovieRating;
import com.javachinna.model.User;
import com.javachinna.repo.MovieRatingsRepository;
import com.javachinna.repo.MovieRepository;
import com.javachinna.service.MovieService;
import com.javachinna.service.OMDBService;
import com.javachinna.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Slf4j
@Service
@AllArgsConstructor
public class MovieServiceImpl implements MovieService {

    private final MovieRepository movieRepository;
    private final MovieRatingsRepository movieRatingsRepository;
    private final OMDBService omdbService;

    private final UserService userService;

    @Override
    public MovieDTO findMovieByTitle(String title) {
        return movieRepository.findByTitle(title).map(m -> new MovieDTO(m.getTitle(), m.isWonOscar() ? "Yes" : "No"))
                .orElseThrow(() -> new ResourceNotFoundException("Movie", "title", title));
    }

    @Override
    public List<MovieDTO> findTop10RatedMovies() {
        List<Movie> movies = movieRepository.findTop10RatedMovies(PageRequest.of(0, 10));
        Executor executor = Executors.newFixedThreadPool(10);
        long start = System.currentTimeMillis();
        var futureMovies = movies.stream().map(m -> CompletableFuture.supplyAsync(() ->
                omdbService.enrichMovieWithBoxOfficeValue(m), executor)).toList();
        var topMovies = futureMovies.stream().map(CompletableFuture::join).collect(toList());
        long end = System.currentTimeMillis();
        System.out.printf("The future operation took %s ms%n", end - start);
        start = System.currentTimeMillis();
        topMovies = movies.stream().map(m ->
                        new MovieDTO(
                                m.getTitle(),
                                m.isWonOscar() ? "Yes" : "No",
                                omdbService.enrichMovieWithBoxOfficeValue(m).getBoxOffice()))
                .collect(Collectors.toList());
        end = System.currentTimeMillis();
        System.out.printf("The normal operation took %s ms%n", end - start);
        topMovies.sort((o1, o2) -> o2.getBoxOffice().compareTo(o1.getBoxOffice()));
        return topMovies;
    }

    @Override
    public void addRating(String title, Integer rating, Principal principal) {
        Movie movie = movieRepository.findByTitle(title).orElseThrow(() -> new ResourceNotFoundException("Movie", "title", title));
        User user = userService.findUserByEmail(principal.getName());
        MovieRating movieRating = movieRatingsRepository.findByMovieTitleAndUserEmail(movie.getTitle(), user.getEmail()).orElse(new MovieRating());
        movieRating.setMovie(movie);
        movieRating.setUser(user);
        movieRating.setRatings(rating);
        movieRatingsRepository.save(movieRating);
    }
}
