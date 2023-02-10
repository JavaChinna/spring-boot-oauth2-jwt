package com.javachinna.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.NoArgsConstructor;
import lombok.Value;

/**
 * Class is used to map the response received from OMDB API
 */
@Value
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class OMDBResponse {
    @JsonProperty("BoxOffice")
    String boxOffice = null;
    @JsonProperty("Response")
    String response = null;
    @JsonProperty("Error")
    String error = null;
}
