package net.pool.station.core.features.transaction.repository.database;

import net.pool.station.core.domain.transaction.TransactionCriteria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TransactionRepository extends JpaRepository<TransactionEntity, Long> {
    Optional<TransactionEntity> findByTransactionCode(String transactionCode);

    @Query("""
        SELECT t
        FROM TransactionEntity t
        LEFT JOIN WalletEntity w ON t.walletId = w.walletId
        LEFT JOIN AccountEntity wa ON w.accountId = wa.accountId
        WHERE wa.accountId = :#{#criteria.accountId()}
            AND (t.createdAt BETWEEN :#{#criteria.timeRange().get(0)} AND :#{#criteria.timeRange().get(1)})
            AND (:#{#criteria.paymentTypeCodes().empty} = TRUE
                    OR t.paymentTypeCode IN :#{#criteria.paymentTypeCodes()})
            AND (:#{#criteria.paymentMethodCodes().empty} = TRUE
                    OR t.paymentMethodCode IN :#{#criteria.paymentMethodCodes()})
            AND (:#{#criteria.statusCodes().empty} = TRUE
                    OR t.statusCode IN :#{#criteria.statusCodes()})
        """)
    Page<TransactionEntity> findAll(TransactionCriteria criteria, Pageable pageable);

    Optional<TransactionEntity> findByMatchMakingIdAndPaymentTypeCode(Long matchMakingId,
                                                                      String paymentTypeCode);

    List<TransactionEntity> findAllByTransactionIdIn(List<Long> transactionIds);
}
