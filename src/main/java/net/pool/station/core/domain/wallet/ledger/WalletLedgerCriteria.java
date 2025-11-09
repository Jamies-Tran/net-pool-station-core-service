package net.pool.station.core.domain.wallet.ledger;

import lombok.Builder;
import net.pool.station.core.bootstrap.configuration.handler.exception.MyAuthenticationException;
import net.pool.station.core.bootstrap.utils.MyDateTimeUtils;
import net.pool.station.core.bootstrap.utils.MyRequestContext;

import java.time.LocalDateTime;
import java.util.List;

@Builder
public record WalletLedgerCriteria(
        Long accountId,
        List<LocalDateTime> timeRange
) {
    public WalletLedgerCriteria {
        accountId = MyRequestContext.getCurrentAccountId().orElseThrow(MyAuthenticationException::new);
        timeRange = MyDateTimeUtils.defaultTimeRange(timeRange);
    }

    public static WalletLedgerCriteria of(
            List<LocalDateTime> timeRange
    ) {
        return WalletLedgerCriteria.builder()
                .timeRange(timeRange)
                .build();
    }
}
