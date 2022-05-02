package org.azul.telemetry.data.model.entity;

import lombok.Builder;
import lombok.Data;
import org.azul.telemetry.data.model.EventType;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Data
@Builder
@Table(name = "events")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    long id;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    User user;

    @Column(name = "event_type")
    @Enumerated(EnumType.STRING)
    EventType eventType;

    @Column(name = "created_at")
    Timestamp createdAt;

    @Column(name = "event_data")
    String eventData;
}
