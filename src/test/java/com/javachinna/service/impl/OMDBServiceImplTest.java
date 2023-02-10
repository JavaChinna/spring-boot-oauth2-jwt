package com.javachinna.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.javachinna.dto.ApiResponse;
import com.javachinna.dto.MovieDTO;
import com.javachinna.dto.OMDBResponse;
import com.javachinna.model.Movie;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {OMDBServiceImpl.class})
@ExtendWith(SpringExtension.class)
class OMDBServiceImplTest {
    @Autowired
    private OMDBServiceImpl oMDBServiceImpl;

    @MockBean
    private RestTemplate restTemplate;

    /**
     * When response is positive
     * Method under test: {@link OMDBServiceImpl#enrichMovieWithBoxOfficeValue(Movie)}
     */
    @Test
    void testEnrichMovieWithBoxOfficeValue() throws RestClientException, JsonProcessingException {
        String responseBody = """
                {
                     "Response": "True",
                     "BoxOffice": "$123,456,12"
                  }""".indent(1);
        mockResponse(responseBody);
        mockAndVerify(12345612L);
    }

    /**
     * When response is negative
     * Method under test: {@link OMDBServiceImpl#enrichMovieWithBoxOfficeValue(Movie)}
     */
    @Test
    void testEnrichMovieWithBoxOfficeValue2() throws RestClientException, JsonProcessingException {
        String responseBody = """
                {
                     "Response": "False",
                     "Error": "Not found"
                  }""".indent(1);

        mockResponse(responseBody);
        mockAndVerify(0L);
    }

    /**
     * When response is null
     * Method under test: {@link OMDBServiceImpl#enrichMovieWithBoxOfficeValue(Movie)}
     */
    @Test
    void testEnrichMovieWithBoxOfficeValue3() throws RestClientException {
        when(restTemplate.getForEntity(any(), (Class<OMDBResponse>) any(), (Object[]) any()))
                .thenReturn(ResponseEntity.ok().body(null));
        mockAndVerify(0L);
    }

    /**
     * When response is 400
     * Method under test: {@link OMDBServiceImpl#enrichMovieWithBoxOfficeValue(Movie)}
     */
    @Test
    void testEnrichMovieWithBoxOfficeValue4() throws RestClientException {
        when(restTemplate.getForEntity(any(), (Class<ApiResponse>) any(), (Object[]) any()))
                .thenReturn(ResponseEntity.badRequest().body(new ApiResponse(false, "failed")));
        mockAndVerify(0L);
    }

    private void mockAndVerify(long expected) {
        Movie movie = new Movie();
        movie.setId(123L);
        movie.setRatings(new ArrayList<>());
        movie.setTitle("Dr");
        movie.setWonOscar(true);
        MovieDTO actualEnrichMovieWithBoxOfficeValueResult = oMDBServiceImpl.enrichMovieWithBoxOfficeValue(movie);
        assertEquals("Yes", actualEnrichMovieWithBoxOfficeValueResult.getWonOscar());
        assertEquals("Dr", actualEnrichMovieWithBoxOfficeValueResult.getTitle());
        assertEquals(expected, actualEnrichMovieWithBoxOfficeValueResult.getBoxOffice().longValue());
        verify(restTemplate).getForEntity(any(), (Class<OMDBResponse>) any(), (Object[]) any());
    }

    private void mockResponse(String responseBody) throws JsonProcessingException {
        OMDBResponse omdbResponse = new ObjectMapper().readValue(responseBody, OMDBResponse.class);
        when(restTemplate.getForEntity(any(), (Class<OMDBResponse>) any(), (Object[]) any()))
                .thenReturn(ResponseEntity.ok().body(omdbResponse));
    }
}

