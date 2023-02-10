package com.javachinna.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginRequest {
	@NotBlank
	private String email;

	@NotBlank
	private String password;
}
