package ru.practicum.statistics.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.practicum.statistics.server.entity.EndpointHit;
import ru.practicum.statistics.server.models.ViewStats;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface EndpointHitRepository extends JpaRepository<EndpointHit, Long> {
    @Query("SELECT new ru.practicum.statistics.server.models.ViewStats(eh.app, eh.uri, COUNT(eh.id)) " +
            "FROM EndpointHit eh " +
            "WHERE eh.timestamp >= :start AND eh.timestamp <= :end " +
            "AND (:uris IS NULL OR eh.uri IN (:uris)) " +
            "GROUP BY eh.uri, eh.app " +
            "ORDER BY COUNT(eh.id) DESC")
    List<ViewStats> getStatsWithoutUniqueIp(@Param("start") LocalDateTime start,
                                            @Param("end") LocalDateTime end,
                                            @Param("uris") List<String> uris);

    @Query("SELECT new ru.practicum.statistics.server.models.ViewStats(eh.app, eh.uri, COUNT(DISTINCT eh.ip)) " +
            "FROM EndpointHit eh " +
            "WHERE eh.timestamp >= :start AND eh.timestamp <= :end " +
            "AND (:uris IS NULL OR eh.uri IN (:uris)) " +
            "GROUP BY eh.uri, eh.app " +
            "ORDER BY COUNT(DISTINCT eh.ip) DESC")
    List<ViewStats> getStatsWithUniqueIp(@Param("start") LocalDateTime start,
                                         @Param("end") LocalDateTime end,
                                         @Param("uris") List<String> uris);


}
