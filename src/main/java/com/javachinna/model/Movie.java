package com.javachinna.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.io.Serial;
import java.util.List;

/**
 * Movie entity maps to the "movies" table in the database
 *
 * @author Chinna
 */
@Entity
@Table(name = "movies")
@Getter
@Setter
@NoArgsConstructor
public class Movie extends BaseEntity {

	/**
	 * 
	 */
	@Serial
	private static final long serialVersionUID = 1493867573442690205L;
	@NotNull
	private String title;

	private boolean wonOscar;

	@JsonIgnore
	@ToString.Exclude
	@OneToMany(mappedBy = "movie", cascade = CascadeType.ALL)
	List<MovieRating> ratings;
}
