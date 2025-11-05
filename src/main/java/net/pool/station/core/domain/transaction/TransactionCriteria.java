package net.pool.station.core.domain.transaction;

import com.fasterxml.jackson.core.type.TypeReference;
import lombok.Builder;
import net.pool.station.core.bootstrap.utils.MyDateTimeUtils;
import net.pool.station.core.bootstrap.utils.MyObjectUtils;

import java.time.LocalDateTime;
import java.util.List;

@Builder
public record TransactionCriteria(
        Long accountId,
        List<LocalDateTime> timeRange,
        List<String> paymentTypeCodes,
        List<String> paymentMethodCodes,
        List<String> statusCodes
) {
    public TransactionCriteria {
        timeRange = MyDateTimeUtils.defaultTimeRange(timeRange);
        paymentTypeCodes = MyObjectUtils.defaultValue(paymentTypeCodes, new TypeReference<>() {});
        paymentMethodCodes = MyObjectUtils.defaultValue(paymentMethodCodes, new TypeReference<>() {});
        statusCodes = MyObjectUtils.defaultValue(statusCodes, new TypeReference<>() {});
    }
}
