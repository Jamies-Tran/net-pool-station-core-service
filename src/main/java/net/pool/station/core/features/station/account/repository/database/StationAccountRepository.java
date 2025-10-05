package net.pool.station.core.features.station.account.repository.database;

import net.pool.station.core.domain.station.account.StationAccountId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StationAccountRepository extends JpaRepository<StationAccountEntity, Long> {
    @Query("""
        SELECT s
        FROM StationAccountEntity s
        WHERE s.stationAccountId.stationId = :#{#stationAccountId.stationId()}
            AND s.stationAccountId.accountId = :#{#stationAccountId.accountId()}
        """)
    Optional<StationAccountEntity> findByStationAccountId(StationAccountId stationAccountId);
}
