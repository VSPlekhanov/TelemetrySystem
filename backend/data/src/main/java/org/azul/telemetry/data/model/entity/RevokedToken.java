package org.azul.telemetry.data.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.hibernate.Hibernate;
import org.jetbrains.annotations.NotNull;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

/**
 * Token that no longer can be used even if it is valid.
 */
@Entity
@Getter
@Setter
@ToString
@Table(name = "revoked_tokens")
@Accessors(chain = true)
@NoArgsConstructor
public class RevokedToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "token", nullable = false, unique = true)
    private String token;

    public RevokedToken(@NotNull String token) {
        this.token = token;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
            return false;
        }

        RevokedToken that = (RevokedToken) o;
        return token != null && Objects.equals(token, that.token);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
