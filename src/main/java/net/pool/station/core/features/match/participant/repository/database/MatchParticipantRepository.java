package net.pool.station.core.features.match.participant.repository.database;

import net.pool.station.core.domain.match.participant.MatchParticipantCriteria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MatchParticipantRepository extends JpaRepository<MatchParticipantEntity, Long> {
    List<MatchParticipantEntity> findAllByMatchMakingId(Long matchMakingId);

    @Query("""
        SELECT m.totalPrice
        FROM MatchMakingEntity m
        WHERE m.matchMakingId = :matchMakingId
        """)
    Integer findTotalPriceByMatchMakingId(Long matchMakingId);

    @Query("""
        SELECT mp
        FROM MatchParticipantEntity mp
        LEFT JOIN AccountEntity a ON mp.accountId = a.accountId
        WHERE mp.matchMakingId = :#{#criteria.matchMakingId()}
        AND (:#{#criteria.search().empty} = TRUE
                OR a.username ILIKE %:#{#criteria.search()}%)
        AND (:#{#criteria.typeCodes().empty} = TRUE
                OR mp.typeCode IN :#{#criteria.typeCodes()})
        AND (:#{#criteria.statusCodes().empty} = TRUE
                OR mp.statusCode IN :#{#criteria.statusCodes()})
        """)
    Page<MatchParticipantEntity> findAll(MatchParticipantCriteria criteria, Pageable pageable);

    @Query("""
        SELECT mp
        FROM MatchParticipantEntity mp
        
        """)
    Boolean existsByHostId(String accountId);
}
