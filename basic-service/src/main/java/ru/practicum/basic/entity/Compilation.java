package ru.practicum.basic.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@Table(name = "compilations")
public class Compilation implements BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "pinned", nullable = false)
    private Boolean pinned;

    @OneToMany(mappedBy = "compilation", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<CompilationEvent> compilationEvents;

    public Compilation(Long id, String title, Boolean pinned) {
        this.id = id;
        this.title = title;
        this.pinned = pinned;
        this.compilationEvents = new HashSet<>();
    }

    public Compilation() {
        this.compilationEvents = new HashSet<>();
    }
}
