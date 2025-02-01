package ru.practicum.basic.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "compilation_events")
public class CompilationEvent implements BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "compilation_id", nullable = false)
    private Compilation compilation;

    @ManyToOne
    @JoinColumn(name = "event_id", nullable = false)
    private Event event;
}

