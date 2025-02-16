package ru.practicum.basic.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.practicum.basic.models.enums.ParticipationRequestStatus;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(
        name = "participation_requests",
        uniqueConstraints = @UniqueConstraint(columnNames = {"event_id", "requester_id"})
)
public class ParticipationRequest implements BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "event_id", nullable = false)
    private Event event;

    @ManyToOne
    @JoinColumn(name = "requester_id", nullable = false)
    private User requester;

    @Column(name = "created", nullable = false)
    private LocalDateTime created;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private ParticipationRequestStatus status;

    @Override
    public String toString() {
        return "ParticipationRequest{" +
                "id=" + id +
                ", event.id=" + (event == null ? null : event.getId()) +
                ", requester.id=" + (requester == null ? null : requester.getId()) +
                ", created=" + created +
                ", status=" + status +
                '}';
    }
}
