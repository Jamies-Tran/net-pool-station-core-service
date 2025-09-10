package net.pool.station.core.features.account.log.repository.database;

import net.pool.station.core.domain.account.log.AccountLogCriteria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountLogRepository extends JpaRepository<AccountLogEntity, Long> {
    @Query("""
        SELECT al
        FROM AccountLogEntity al
        WHERE al.accountId = :#{#criteria.accountId()}
            AND (al.createdAt BETWEEN :#{#criteria.timeRange().get(0)}
                AND :#{#criteria.timeRange().get(1)})
            AND (:#{#criteria.search().empty} = TRUE
                    OR al.logTypeName ILIKE %:#{#criteria.search()}%)
            AND (:#{#criteria.logTypeCodes().empty} = TRUE
                    OR al.logTypeName IN :#{#criteria.logTypeCodes()})
        """)
    Page<AccountLogEntity> findAll(AccountLogCriteria criteria, Pageable pageable);
}
