package net.pool.station.core.features.login.log.repository.database;

import net.pool.station.core.domain.login.log.LoginLogCriteria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface LoginLogRepository extends JpaRepository<LoginLogEntity, Long> {

    @Query("""
        SELECT ll
        FROM LoginLogEntity ll
        WHERE ll.accountId = :#{#criteria.accountId()}
            AND (ll.createdAt BETWEEN :#{#criteria.timeRange().get(0)}
                    AND :#{#criteria.timeRange().get(1)})
            AND (:#{#criteria.search().empty} = TRUE
                    OR ll.logTypeName ILIKE %:#{#criteria.search()}%)
            AND (:#{#criteria.logTypeCodes().empty} = TRUE
                    OR ll.logTypeCode IN :#{#criteria.logTypeCodes()})
        """)
    Page<LoginLogEntity> findAll(LoginLogCriteria criteria, Pageable pageable);
}
