package com.javachinna.service.impl;

import com.javachinna.dto.MovieDTO;
import com.javachinna.dto.OMDBResponse;
import com.javachinna.model.Movie;
import com.javachinna.service.OMDBService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.text.NumberFormat;
import java.util.Locale;

@Slf4j
@Service
@RequiredArgsConstructor
public class OMDBServiceImpl implements OMDBService {
    @Value("${omdb.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate;

    @Override
    public MovieDTO enrichMovieWithBoxOfficeValue(Movie movie) {
        MovieDTO movieDTO = new MovieDTO(movie.getTitle(), movie.isWonOscar() ? "Yes" : "No");
        try {
            ResponseEntity<OMDBResponse> response = restTemplate.getForEntity("https://www.omdbapi.com/?apikey={key}&t={title}", OMDBResponse.class, apiKey, movie.getTitle());
            if (response.getBody() != null) {
                OMDBResponse omdbResponse = response.getBody();
                if ("True".equalsIgnoreCase(omdbResponse.getResponse())) {
                    movieDTO.setBoxOffice(NumberFormat.getCurrencyInstance(Locale.US).parse(omdbResponse.getBoxOffice()).longValue());
                } else throw new RestClientException(omdbResponse.getError());

            } else throw new RestClientException("No response received from OMDB API");
        } catch (Exception e) {
            movieDTO.setBoxOffice(0L);
            log.error("Unable to fetch box office value for movie: {}, error: {}", movie.getTitle(), e.getMessage());
        }
        return movieDTO;
    }
}
