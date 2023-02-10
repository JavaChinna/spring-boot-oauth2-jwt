package com.javachinna.service;

import com.javachinna.dto.SignUpRequest;
import com.javachinna.exception.UserAlreadyExistAuthenticationException;
import com.javachinna.model.User;

/**
 * Service interface for user operations
 *
 * @author Chinna
 * @since 06/11/22
 */
public interface UserService {

    User findUserByEmail(String email);

    User registerNewUser(SignUpRequest signUpRequest) throws UserAlreadyExistAuthenticationException;
}
