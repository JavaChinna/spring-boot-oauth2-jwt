package com.javachinna.repo;

import com.javachinna.model.Movie;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * JPA Repository for movie entity
 *
 * @author Chinna
 */
@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {
    Optional<Movie> findByTitle(String title);
    @Query("select m from Movie m left join m.ratings r group by m order by avg(r.ratings) desc")
    List<Movie> findTop10RatedMovies(Pageable pageable);
}
