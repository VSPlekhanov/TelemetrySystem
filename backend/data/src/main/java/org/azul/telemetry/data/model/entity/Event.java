package org.azul.telemetry.data.model.entity;

import com.vladmihalcea.hibernate.type.basic.PostgreSQLEnumType;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import org.azul.telemetry.data.model.EventType;
import org.hibernate.Hibernate;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@ToString
@Builder
@Table(name = "events", schema = "azul_schema")
@TypeDef(name = "pgsql_enum", typeClass = PostgreSQLEnumType.class)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    User user;

    @Column(name = "event_type", columnDefinition = "event_types")
    @Enumerated(EnumType.STRING)
    @Type(type = "pgsql_enum")
    EventType eventType;

    @Column(name = "created_at")
    Timestamp createdAt;

    @Column(name = "event_data")
    @Type(type = "json")
    String eventData;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Event event = (Event) o;

        return Objects.equals(id, event.id);
    }

    @Override
    public int hashCode() {
        return 1491041522;
    }
}