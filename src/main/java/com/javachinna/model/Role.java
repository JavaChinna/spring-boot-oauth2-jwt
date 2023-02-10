package com.javachinna.model;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Role extends BaseEntity {
	@Serial
	private static final long serialVersionUID = 1L;
	public static final String ROLE_USER = "ROLE_USER";
	public static final String ROLE_ADMIN = "ROLE_ADMIN";

	private String name;

	public Role(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
        return "Role [name=" + name + "]" + "[id=" + id + "]";
	}
}
