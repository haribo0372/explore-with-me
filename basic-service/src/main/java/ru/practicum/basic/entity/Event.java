package ru.practicum.basic.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.practicum.basic.models.enums.EventsLifeCycle;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "events")
public class Event implements BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "annotation", nullable = false)
    private String annotation;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @Transient
    private Long confirmedRequests;

    @Column(name = "createdOn", nullable = false)
    private LocalDateTime createdOn;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "eventDate", nullable = false)
    private LocalDateTime eventDate;

    @ManyToOne
    @JoinColumn(name = "initiator", nullable = false)
    private User initiator;

    @Column(name = "location_lat")
    private Float locationLat;

    @Column(name = "location_lon")
    private Float locationLon;

    @Column(name = "paid")
    private Boolean paid;

    @Column(name = "participantLimit")
    private Integer participantLimit;

    @Column(name = "publishedOn", nullable = false)
    private LocalDateTime publishedOn;

    @Column(name = "requestModeration", nullable = false)
    private Boolean requestModeration;

    @Column(name = "state")
    @Enumerated(EnumType.STRING)
    private EventsLifeCycle state;

    @Column(name = "views")
    private Long views;
}
