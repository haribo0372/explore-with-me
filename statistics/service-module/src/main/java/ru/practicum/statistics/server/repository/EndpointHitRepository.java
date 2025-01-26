package ru.practicum.statistics.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.practicum.statistics.entity.EndpointHit;
import ru.practicum.statistics.models.ViewStats;

import java.time.LocalDateTime;
import java.util.Collection;

@Repository
public interface EndpointHitRepository extends JpaRepository<EndpointHit, Long> {
    @Query("SELECT new ru.practicum.statistics.models.ViewStats(eh.app, eh.uri, COUNT(eh.id)) " +
            "FROM EndpointHit eh " +
            "WHERE eh.timestamp >= :start AND eh.timestamp <= :end AND eh.uri = :uri " +
            "GROUP BY eh.uri, eh.app")
    Collection<ViewStats> getStatsWithoutUniqueIp(@Param("start") LocalDateTime start,
                                                  @Param("end") LocalDateTime end,
                                                  @Param("uri") String uri);

    @Query("SELECT new ru.practicum.statistics.models.ViewStats(eh.app, eh.uri, COUNT(DISTINCT eh.ip)) " +
            "FROM EndpointHit eh " +
            "WHERE eh.timestamp >= :start AND eh.timestamp <= :end AND eh.uri = :uri " +
            "GROUP BY eh.uri, eh.app")
    Collection<ViewStats> getStatsWithUniqueIp(@Param("start") LocalDateTime start,
                                               @Param("end") LocalDateTime end,
                                               @Param("uri") String uri);
}
