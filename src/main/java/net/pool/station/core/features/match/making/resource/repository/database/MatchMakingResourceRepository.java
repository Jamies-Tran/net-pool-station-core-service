package net.pool.station.core.features.match.making.resource.repository.database;

import net.pool.station.core.features.match.making.resource.repository.database.dao.MatchMakingResourceDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MatchMakingResourceRepository extends JpaRepository<MatchMakingResourceEntity, Long> {
    @Query("""
        DELETE
        FROM MatchMakingResourceEntity mmr
        WHERE mmr.id.matchMakingId = :matchMakingId
        """)
    @Modifying
    void deleteAllByMatchMakingId(Long matchMakingId);

    @Query("""
        SELECT 
                mmr.id AS id,
                sr.typeCode AS typeCode,
                sr.typeName AS typeName,
                a.price AS price
        FROM MatchMakingResourceEntity mmr
        INNER JOIN StationResourceEntity sr ON mmr.id.stationResourceId = sr.stationResourceId
        INNER JOIN AreaEntity a ON a.areaId = sr.areaId
        WHERE mmr.id.matchMakingId = :matchMakingId
        """)
    List<MatchMakingResourceDao> findAllByMatchMakingId(Long matchMakingId);
}
