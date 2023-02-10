package com.javachinna.dto;

import com.javachinna.validator.PasswordMatches;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * @author Chinna
 * @since 26/2/22
 */
@Data
@PasswordMatches
public class SignUpRequest {

	@NotEmpty
	private String displayName;

	@NotEmpty
	private String email;

	@Size(min = 6, message = "Minimum 6 chars required")
	private String password;

	@NotEmpty
	private String matchingPassword;

	private boolean using2FA;

	public SignUpRequest(String displayName, String email, String password, String matchingPassword) {
		this.displayName = displayName;
		this.email = email;
		this.password = password;
		this.matchingPassword = matchingPassword;
	}
}
