package com.javachinna.dto;

import lombok.*;

/**
 * DTO for holding movie info
 */
@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class MovieDTO {
    private final String title;
    private final String wonOscar;
    private Long boxOffice;
}