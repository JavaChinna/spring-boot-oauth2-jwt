package com.javachinna.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Range;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.io.Serial;

/**
 * This entity maps to the "ratings" table in the database
 *
 * @author Chinna
 */
@Entity
@Table(name = "ratings")
@Getter
@Setter
@NoArgsConstructor
public class MovieRating extends BaseEntity {

    /**
     *
     */
    @Serial
    private static final long serialVersionUID = -467324267912994552L;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "movie_id", nullable = false)
    private Movie movie;

    @NotNull
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", nullable = false)
    @ToString.Exclude
    private User user;

    @Range(min = 1, max = 10)
    private Integer ratings;
}
