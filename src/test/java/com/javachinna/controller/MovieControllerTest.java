package com.javachinna.controller;

import com.javachinna.service.MovieService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.security.Principal;

@ContextConfiguration(classes = {MovieController.class})
@ExtendWith(SpringExtension.class)
class MovieControllerTest {
    @Autowired
    private MovieController movieController;

    @MockBean
    private MovieService movieService;

    /**
     * Method under test: {@link MovieController#addRating(String, Integer, Principal)}
     */
    @Test
    void testAddRating() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/movies/{title}/rating/{rating}", "Avatar", 1);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(movieController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.content().json("{\"success\":true}"));
    }

    /**
     * Method under test: {@link MovieController#findMovie(String)}
     */
    @Test
    void testFindMovie() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/movies/{title}", "Avatar");
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(movieController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isOk());
    }

    /**
     * Method under test: {@link MovieController#findTop10RatedMovies()}
     */
    @Test
    void testFindTop10RatedMovies() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/movies");
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(movieController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isOk());
    }
}

