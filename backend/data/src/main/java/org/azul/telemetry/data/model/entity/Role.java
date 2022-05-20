package org.azul.telemetry.data.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.hibernate.Hibernate;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Objects;
import java.util.Set;

/**
 * User role entity.
 * Role identified by it`s name.
 * Name must match pattern "ROLE_&lt role_name &gt"
 */
@Entity
@Getter
@Setter
@ToString
@Accessors(chain = true)
@NoArgsConstructor
@Table(name = "roles")
public class Role implements GrantedAuthority {
    @Transient
    public static final String ADMIN_ROLE_NAME = "ROLE_ADMIN";
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name", nullable = false, unique = true)
    private String name;
    @NotNull
    @Transient
    @ToString.Exclude
    @ManyToMany(mappedBy = "roles")
    private Set<User> users = Set.of();

    public Role(@NotNull String name) {
        this.name = name;
    }

    @Override
    public String getAuthority() {
        return getName();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
            return false;
        }

        Role role = (Role) o;
        return Objects.equals(name, role.name);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
