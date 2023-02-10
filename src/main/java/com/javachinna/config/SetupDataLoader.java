package com.javachinna.config;

import com.javachinna.model.Movie;
import com.javachinna.model.Role;
import com.javachinna.model.User;
import com.javachinna.repo.MovieRepository;
import com.javachinna.repo.RoleRepository;
import com.javachinna.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileReader;
import java.io.Reader;

/**
 * Class is responsible for initializing the database with users and movies from CSV file on application startup
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class SetupDataLoader implements ApplicationListener<ContextRefreshedEvent> {

    private boolean alreadySetup = false;

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final MovieRepository movieRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void onApplicationEvent(final ContextRefreshedEvent event) {
        if (alreadySetup || userRepository.findAll().iterator().hasNext()) {
            return;
        }

        // Create user roles
        var userRole = createRoleIfNotFound(Role.ROLE_USER);
        var adminRole = createRoleIfNotFound(Role.ROLE_ADMIN);

        // Create users
        createUserIfNotFound("user@javachinna.com", passwordEncoder.encode("user@@"), // "user"
                userRole, "User");
        createUserIfNotFound("admin@javachinna.com", passwordEncoder.encode("admin@"), // "admin"
                adminRole, "Administrator");
        insertMoviesFromCSV();
        alreadySetup = true;
    }

    @Transactional
    void createUserIfNotFound(final String email, final String password, final Role role, final String displayName) {
        User user = userRepository.findByEmailIgnoreCase(email);
        if (user == null) {
            user = new User(email, password);
            user.addRole(role);
            user.setEnabled(true);
            user.setDisplayName(displayName);
            userRepository.save(user);
        }
    }

    @Transactional
    Role createRoleIfNotFound(final String name) {
        Role role = roleRepository.findByName(name);
        if (role == null) {
            role = new Role(name);
            role = roleRepository.save(role);
        }
        return role;
    }

    void insertMoviesFromCSV() {
        try (Reader in = new FileReader(new ClassPathResource("academy_awards.csv").getFile())) {
            CSVFormat.RFC4180.builder()
                    .setAllowMissingColumnNames(true).setHeader("Year", "Category", "Nominee", "Additional Info", "Won?")
                    .setSkipHeaderRecord(true).build().parse(in).forEach(record -> {
                        if ("Best Picture".equals(record.get("Category"))) {
                            Movie movie = new Movie();
                            movie.setTitle(record.get("Nominee"));
                            movie.setWonOscar("Yes".equalsIgnoreCase(record.get("Won?")));
                            movieRepository.save(movie);
                        }
                    });
        } catch (Exception e) {
            log.error("Unable to read CSV file", e);
        }
    }
}
