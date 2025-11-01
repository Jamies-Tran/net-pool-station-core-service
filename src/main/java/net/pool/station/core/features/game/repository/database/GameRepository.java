package net.pool.station.core.features.game.repository.database;

import net.pool.station.core.domain.game.GameCriteria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GameRepository extends JpaRepository<GameEntity, Long> {
    @Query("""
        SELECT s.stationCode
        FROM StationSpaceEntity ss
        INNER JOIN StationEntity s ON s.stationId = ss.stationId
        WHERE ss.stationSpaceId = :stationSpaceId
        """)
    Optional<String> findStationCodeByStationSpaceId(Long stationSpaceId);

    Optional<GameEntity> findByGameIdAndDeletedFalse(Long gameId);

    @Query("""
        SELECT g
        FROM GameEntity g
        LEFT JOIN StationSpaceEntity ss ON g.stationSpaceId = ss.stationSpaceId
        WHERE g.deleted = FALSE
                AND ss.stationSpaceId = :#{#criteria.stationSpaceId()}
                AND (:#{#criteria.search().empty} = TRUE
                        OR (g.gameName ILIKE %:#{#criteria.search()}%
                                OR g.gameCode = :#{#criteria.search()}))
                AND (:#{#criteria.genreCodes().empty} = TRUE
                        OR g.genreCode IN :#{#criteria.genreCodes()})
                AND (:#{#criteria.statusCodes().empty} = TRUE
                        OR g.statusCode IN :#{#criteria.statusCodes()})
        """)
    Page<GameEntity> findAll(GameCriteria criteria, Pageable pageable);
}
