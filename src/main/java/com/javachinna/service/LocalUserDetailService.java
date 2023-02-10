package com.javachinna.service;

import com.javachinna.dto.LocalUser;
import com.javachinna.model.User;
import com.javachinna.util.CommonUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementation for {@link UserDetailsService}
 *
 * @author Chinna
 */
@Service
@RequiredArgsConstructor
public class LocalUserDetailService implements UserDetailsService {

    private final UserService userService;

    @Override
    @Transactional
    public LocalUser loadUserByUsername(final String email) throws UsernameNotFoundException {
        User user = userService.findUserByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("User " + email + " was not found in the database");
        }
        return createLocalUser(user);
    }

    /**
     * @param user The user entity
     * @return LocalUser The spring user object
     */
    private LocalUser createLocalUser(User user) {
        return new LocalUser(user.getEmail(), user.getPassword(), user.isEnabled(), true, true, true, CommonUtils.buildSimpleGrantedAuthorities(user.getRoles()));
    }
}
