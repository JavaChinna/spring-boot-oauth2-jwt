package com.javachinna.model;

import com.javachinna.model.pk.UserRolePK;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class UserRole implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L;

	/**
	 * @param user The user entity
	 * @param role The role entity
	 */
	public UserRole(User user, Role role) {
		this.id = new UserRolePK(user.getId(), role.getId());
		this.role = role;
		this.user = user;
	}

	@EmbeddedId
	private UserRolePK id;

	@ManyToOne(fetch = FetchType.LAZY)
	@MapsId("userId")
	private User user;

	@ManyToOne(fetch = FetchType.LAZY)
	@MapsId("roleId")
	private Role role;

	protected boolean deleted;

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return Objects.hash(role, user);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserRole other = (UserRole) obj;
		return Objects.equals(role, other.role) && Objects.equals(user, other.user);
	}

}
