package com.javachinna.service.impl;

import com.javachinna.dto.MovieDTO;
import com.javachinna.exception.ResourceNotFoundException;
import com.javachinna.model.Movie;
import com.javachinna.model.MovieRating;
import com.javachinna.model.User;
import com.javachinna.repo.MovieRatingsRepository;
import com.javachinna.repo.MovieRepository;
import com.javachinna.service.OMDBService;
import com.javachinna.service.UserService;
import com.sun.security.auth.UserPrincipal;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {MovieServiceImpl.class})
@ExtendWith(SpringExtension.class)
class MovieServiceImplTest {
    @MockBean
    private MovieRatingsRepository movieRatingsRepository;

    @MockBean
    private MovieRepository movieRepository;

    @Autowired
    private MovieServiceImpl movieServiceImpl;

    @MockBean
    private OMDBService oMDBService;

    @MockBean
    private UserService userService;

    /**
     * Method under test: {@link MovieServiceImpl#findMovieByTitle(String)}
     */
    @Test
    void testFindMovieByTitle() {
        Movie movie = new Movie();
        movie.setId(123L);
        movie.setRatings(new ArrayList<>());
        movie.setTitle("Dr");
        movie.setWonOscar(true);
        Optional<Movie> ofResult = Optional.of(movie);
        when(movieRepository.findByTitle(any())).thenReturn(ofResult);
        MovieDTO actualFindMovieByTitleResult = movieServiceImpl.findMovieByTitle("Dr");
        assertEquals("Yes", actualFindMovieByTitleResult.getWonOscar());
        assertEquals("Dr", actualFindMovieByTitleResult.getTitle());
        verify(movieRepository).findByTitle((String) any());
    }

    /**
     * Method under test: {@link MovieServiceImpl#findMovieByTitle(String)}
     */
    @Test
    void testFindMovieByTitle2() {
        Movie movie = mock(Movie.class);
        when(movie.isWonOscar()).thenReturn(false);
        when(movie.getTitle()).thenReturn("Dr");
        doNothing().when(movie).setId((Long) any());
        doNothing().when(movie).setRatings((List<MovieRating>) any());
        doNothing().when(movie).setTitle((String) any());
        doNothing().when(movie).setWonOscar(anyBoolean());
        movie.setId(123L);
        movie.setRatings(new ArrayList<>());
        movie.setTitle("Dr");
        movie.setWonOscar(true);
        Optional<Movie> ofResult = Optional.of(movie);
        when(movieRepository.findByTitle((String) any())).thenReturn(ofResult);
        MovieDTO actualFindMovieByTitleResult = movieServiceImpl.findMovieByTitle("Dr");
        assertEquals("No", actualFindMovieByTitleResult.getWonOscar());
        assertEquals("Dr", actualFindMovieByTitleResult.getTitle());
        verify(movieRepository).findByTitle((String) any());
        verify(movie).isWonOscar();
        verify(movie).getTitle();
        verify(movie).setId((Long) any());
        verify(movie).setRatings((List<MovieRating>) any());
        verify(movie).setTitle((String) any());
        verify(movie).setWonOscar(anyBoolean());
    }

    /**
     * Method under test: {@link MovieServiceImpl#findMovieByTitle(String)}
     */
    @Test
    void testFindMovieByTitle3() {
        when(movieRepository.findByTitle(any())).thenReturn(Optional.empty());
        Movie movie = mock(Movie.class);
        when(movie.isWonOscar()).thenReturn(true);
        when(movie.getTitle()).thenReturn("Dr");
        doNothing().when(movie).setId((Long) any());
        doNothing().when(movie).setRatings((List<MovieRating>) any());
        doNothing().when(movie).setTitle((String) any());
        doNothing().when(movie).setWonOscar(anyBoolean());
        movie.setId(123L);
        movie.setRatings(new ArrayList<>());
        movie.setTitle("Dr");
        movie.setWonOscar(true);
        assertThrows(ResourceNotFoundException.class, () -> movieServiceImpl.findMovieByTitle("Dr"));
        verify(movieRepository).findByTitle((String) any());
        verify(movie).setId((Long) any());
        verify(movie).setRatings((List<MovieRating>) any());
        verify(movie).setTitle((String) any());
        verify(movie).setWonOscar(anyBoolean());
    }

    /**
     * Method under test: {@link MovieServiceImpl#findMovieByTitle(String)}
     */
    @Test
    void testFindMovieByTitle4() {
        Movie movie = new Movie();
        movie.setId(123L);
        movie.setRatings(new ArrayList<>());
        movie.setTitle("Dr");
        movie.setWonOscar(true);
        Optional<Movie> ofResult = Optional.of(movie);
        when(movieRepository.findByTitle((String) any())).thenReturn(ofResult);
        MovieDTO actualFindMovieByTitleResult = movieServiceImpl.findMovieByTitle("Dr");
        assertEquals("Yes", actualFindMovieByTitleResult.getWonOscar());
        assertEquals("Dr", actualFindMovieByTitleResult.getTitle());
        verify(movieRepository).findByTitle((String) any());
    }

    /**
     * Method under test: {@link MovieServiceImpl#findMovieByTitle(String)}
     */
    @Test
    void testFindMovieByTitle5() {
        when(movieRepository.findByTitle((String) any())).thenReturn(Optional.empty());
        Movie movie = mock(Movie.class);
        when(movie.isWonOscar()).thenReturn(true);
        when(movie.getTitle()).thenReturn("Dr");
        doNothing().when(movie).setId((Long) any());
        doNothing().when(movie).setRatings((List<MovieRating>) any());
        doNothing().when(movie).setTitle((String) any());
        doNothing().when(movie).setWonOscar(anyBoolean());
        movie.setId(123L);
        movie.setRatings(new ArrayList<>());
        movie.setTitle("Dr");
        movie.setWonOscar(true);
        assertThrows(ResourceNotFoundException.class, () -> movieServiceImpl.findMovieByTitle("Dr"));
        verify(movieRepository).findByTitle(any());
        verify(movie).setId(any());
        verify(movie).setRatings(any());
        verify(movie).setTitle(any());
        verify(movie).setWonOscar(anyBoolean());
    }

    /**
     * Method under test: {@link MovieServiceImpl#findTop10RatedMovies()}
     */
    @Test
    void testFindTop10RatedMovies() {
        when(movieRepository.findTop10RatedMovies(any())).thenReturn(new ArrayList<>());
        assertTrue(movieServiceImpl.findTop10RatedMovies().isEmpty());
        verify(movieRepository).findTop10RatedMovies(any());
    }

    /**
     * Method under test: {@link MovieServiceImpl#findTop10RatedMovies()}
     */
    @Test
    void testFindTop10RatedMovies2() {
        Movie movie = new Movie();
        movie.setId(123L);
        movie.setRatings(new ArrayList<>());
        movie.setTitle("Dr");
        movie.setWonOscar(true);

        ArrayList<Movie> movieList = new ArrayList<>();
        movieList.add(movie);
        when(movieRepository.findTop10RatedMovies(any())).thenReturn(movieList);
        when(oMDBService.enrichMovieWithBoxOfficeValue(any())).thenReturn(new MovieDTO("Dr", "Won Oscar"));
        assertEquals(1, movieServiceImpl.findTop10RatedMovies().size());
        verify(movieRepository).findTop10RatedMovies(any());
        verify(oMDBService).enrichMovieWithBoxOfficeValue(any());
    }

    /**
     * Test movie sorting by box office value
     * Method under test: {@link MovieServiceImpl#findTop10RatedMovies()}
     */
    @Test
    void testFindTop10RatedMovies3() {
        Movie movie = new Movie();
        movie.setId(123L);
        movie.setRatings(new ArrayList<>());
        movie.setTitle("Dr");
        movie.setWonOscar(true);

        Movie movie1 = new Movie();
        movie1.setId(123L);
        movie.setRatings(new ArrayList<>());
        movie1.setTitle("Dr");
        movie1.setWonOscar(true);

        ArrayList<Movie> movieList = new ArrayList<>();
        movieList.add(movie1);
        movieList.add(movie);
        when(movieRepository.findTop10RatedMovies(any())).thenReturn(movieList);
        when(oMDBService.enrichMovieWithBoxOfficeValue(movie)).thenReturn(new MovieDTO("Avatar", "Won Oscar", 1234L));
        when(oMDBService.enrichMovieWithBoxOfficeValue(movie1)).thenReturn(new MovieDTO("Inception", "Won Oscar", 123456L));
        List<MovieDTO> list = movieServiceImpl.findTop10RatedMovies();
        assertEquals(2, list.size());
        assertEquals("Inception", list.get(0).getTitle());
        verify(movieRepository, times(1)).findTop10RatedMovies(any());
        verify(oMDBService, times(2)).enrichMovieWithBoxOfficeValue(any());
    }


    /**
     * Test positive scenario
     * Method under test: {@link MovieServiceImpl#addRating(String, Integer, Principal)}
     */
    @Test
    void testAddRating() {
        Movie movie = new Movie();
        movie.setId(123L);
        movie.setRatings(new ArrayList<>());
        movie.setTitle("Dr");
        movie.setWonOscar(true);
        Optional<Movie> ofResult = Optional.of(movie);
        when(movieRepository.findByTitle((String) any())).thenReturn(ofResult);

        Movie movie1 = new Movie();
        movie1.setId(123L);
        movie1.setRatings(new ArrayList<>());
        movie1.setTitle("Dr");
        movie1.setWonOscar(true);

        User user = new User();
        user.setDisplayName("Display Name");
        user.setEmail("jane.doe@example.org");
        user.setId(123L);
        user.setPassword("iloveyou");

        MovieRating movieRating = new MovieRating();
        movieRating.setId(123L);
        movieRating.setMovie(movie1);
        movieRating.setRatings(1);
        movieRating.setUser(user);

        Movie movie2 = new Movie();
        movie2.setId(123L);
        movie2.setRatings(new ArrayList<>());
        movie2.setTitle("Dr");
        movie2.setWonOscar(true);

        User user1 = new User();
        user1.setDisplayName("Display Name");
        user1.setEmail("jane.doe@example.org");
        user1.setId(123L);
        user1.setPassword("iloveyou");

        MovieRating movieRating1 = new MovieRating();
        movieRating1.setId(123L);
        movieRating1.setMovie(movie2);
        movieRating1.setRatings(1);
        movieRating1.setUser(user1);
        Optional<MovieRating> ofResult1 = Optional.of(movieRating1);
        when(movieRatingsRepository.save((MovieRating) any())).thenReturn(movieRating);
        when(movieRatingsRepository.findByMovieTitleAndUserEmail((String) any(), (String) any())).thenReturn(ofResult1);

        User user2 = new User();
        user2.setDisplayName("Display Name");
        user2.setEmail("jane.doe@example.org");
        user2.setId(123L);
        user2.setPassword("iloveyou");
        when(userService.findUserByEmail((String) any())).thenReturn(user2);
        movieServiceImpl.addRating("Dr", 2, new UserPrincipal("principal"));
        verify(movieRepository).findByTitle((String) any());
        verify(movieRatingsRepository).save((MovieRating) any());
        verify(movieRatingsRepository).findByMovieTitleAndUserEmail((String) any(), (String) any());
        verify(userService).findUserByEmail((String) any());
    }

    /**
     * Test negative scenario
     * Method under test: {@link MovieServiceImpl#addRating(String, Integer, Principal)}
     */
    @Test
    void testAddRating2() {
        Movie movie = new Movie();
        movie.setId(123L);
        movie.setRatings(new ArrayList<>());
        movie.setTitle("Dr");
        movie.setWonOscar(true);
        Optional<Movie> ofResult = Optional.of(movie);
        when(movieRepository.findByTitle(any())).thenReturn(ofResult);
        when(movieRatingsRepository.findByMovieTitleAndUserEmail((String) any(), (String) any()))
                .thenThrow(new ResourceNotFoundException("Resource Name", "Field Name", "Field Value"));

        User user = new User();
        user.setDisplayName("Display Name");
        user.setEmail("jane.doe@example.org");
        user.setId(123L);
        user.setPassword("iloveyou");
        when(userService.findUserByEmail((String) any())).thenReturn(user);
        assertThrows(ResourceNotFoundException.class,
                () -> movieServiceImpl.addRating("Dr", 2, new UserPrincipal("principal")));
        verify(movieRepository).findByTitle((String) any());
        verify(movieRatingsRepository).findByMovieTitleAndUserEmail((String) any(), (String) any());
        verify(userService).findUserByEmail((String) any());
    }

}

