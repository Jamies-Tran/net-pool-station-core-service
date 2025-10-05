package net.pool.station.core.features.account.account.repository.database;

import net.pool.station.core.domain.account.AccountCriteria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<AccountEntity, Long> {
    @Query("""
        SELECT a
        FROM AccountEntity a
        WHERE a.deleted = FALSE
                AND a.email = :email
        """)
    Optional<AccountEntity> findByEmail(String email);

    @Query("""
        SELECT a
        FROM AccountEntity a
        WHERE a.deleted = FALSE
                AND a.identification = :identification
        """)
    Optional<AccountEntity> findByIdentification(String identification);

    @Query("""
        SELECT a
        FROM AccountEntity a
        WHERE a.deleted = FALSE
                AND a.phone = :phone
        """)
    Optional<AccountEntity> findByPhone(String phone);


    @Query("""
        SELECT a
        FROM AccountEntity a
        WHERE a.deleted = FALSE
                AND a.accountId = :accountId
        """)
    Optional<AccountEntity> findByAccountId(Long accountId);

    @Query("""
        SELECT a
        FROM AccountEntity a
        WHERE a.deleted = FALSE
                AND a.username = :username
        """)
    Optional<AccountEntity> findByUsername(String username);

    @Query("""
        SELECT a
        FROM AccountEntity a
        LEFT JOIN StationAccountEntity sa ON a.accountId = sa.stationAccountId.accountId
        LEFT JOIN StationEntity s ON s.stationId = sa.stationAccountId.stationId
        WHERE a.deleted = FALSE
                AND (a.createdAt BETWEEN :#{#criteria.timeRange().get(0)} 
                        AND :#{#criteria.timeRange().get(1)})
                AND (:#{#criteria.stationId()} = 0
                        OR s.stationId = :#{#criteria.stationId()})
                AND (:#{#criteria.search().empty} = TRUE
                        OR a.username ILIKE %:#{#criteria.search()}%
                        OR a.email ILIKE %:#{#criteria.search()}%
                        OR a.identification ILIKE %:#{#criteria.search()}%)
                AND (:#{#criteria.statusCodes().empty} = TRUE
                        OR a.statusCode IN :#{#criteria.statusCodes()})
                AND (:#{#criteria.roleIds().empty} = TRUE
                        OR a.roleId IN :#{#criteria.roleIds()})
        """)
    Page<AccountEntity> findAll(AccountCriteria criteria, Pageable pageable);

    @Query("""
        SELECT a
        FROM AccountEntity a
        WHERE a.deleted = FALSE
                AND a.accountId IN :accountIds
        """)
    List<AccountEntity> findAllByAccountIdIn(List<Long> accountIds);
}
