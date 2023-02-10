package com.javachinna.repo;

import com.javachinna.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * JPA Repository for user entity
 *
 * @author Chinna
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	User findByEmailIgnoreCase(String email);
	boolean existsByEmailIgnoreCase(String email);
}
