package com.javachinna.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.NaturalId;

import java.io.Serial;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

@Entity
@Table(name = "user")
@Getter
@Setter
@NoArgsConstructor
public class User extends BaseEntity{

	/**
	 * 
	 */
	@Serial
	private static final long serialVersionUID = -467324267912994552L;

	@NaturalId(mutable = true)
	@Column(name = "email", unique = true, nullable = false)
	private String email;

	@NotNull
	private String password;

	@Column(name = "DISPLAY_NAME")
	private String displayName;

	@Column(name = "enabled", columnDefinition = "BIT", length = 1)
	private boolean enabled;

	@Column(name = "USING_2FA")
	private boolean using2FA;

	private String secret;

	@JsonManagedReference
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<UserRole> roles = new HashSet<>();

	public User(String email, String password) {
		this.email = email;
		this.password = password;
	}

	public void addRole(Role role) {
		UserRole userRole = new UserRole(this, role);
		roles.add(userRole);
	}

	public void removeRole(Role role) {
		for (Iterator<UserRole> iterator = roles.iterator(); iterator.hasNext();) {
			UserRole userRole = iterator.next();

			if (userRole.getUser().equals(this) && userRole.getRole().equals(role)) {
				iterator.remove();
				userRole.setUser(null);
				userRole.setRole(null);
			}
		}
	}
}
