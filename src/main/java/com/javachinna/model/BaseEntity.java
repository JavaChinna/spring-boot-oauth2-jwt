package com.javachinna.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

/**
 * Base class for all JPA entities
 *
 * @author Chinna
 */
@Getter
@Setter
@MappedSuperclass
public abstract class BaseEntity implements Serializable {

    /**
     *
     */
    @Serial
    private static final long serialVersionUID = -7363399724812884337L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    protected Long id;

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (!this.getClass().isInstance(o))
            return false;

        BaseEntity other = (BaseEntity) o;

        return id != null && id.equals(other.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
