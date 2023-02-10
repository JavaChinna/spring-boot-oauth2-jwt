package com.javachinna.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AuthControllerTest {
    @Autowired
    MockMvc mvc;

    @Test
    void whenAuthenticatedThenSaysOk() throws Exception {
        // @formatter:off
        MvcResult result = this.mvc.perform(post("/api/auth/token")
                        .with(httpBasic("user@javachinna.com", "user@@")))
                .andExpect(status().isOk())
                .andReturn();

        String token = result.getResponse().getContentAsString();

        this.mvc.perform(get("/api/movies/avatar")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("{\"title\":\"Avatar\"}"));
        // @formatter:on
    }

    @Test
    void whenUnauthenticatedThen401() throws Exception {
        // @formatter:off
        this.mvc.perform(get("/api/movies"))
                .andExpect(status().isUnauthorized());
        // @formatter:on
    }

    @Test
    void whenBadCredentialsThen401() throws Exception {
        // @formatter:off
        this.mvc.perform(post("/api/auth/token"))
                .andExpect(status().isUnauthorized());
        // @formatter:on
    }
}

