package net.pool.station.core.domain.login.log;

import lombok.Builder;
import net.pool.station.core.bootstrap.utils.MyDateTimeUtils;
import net.pool.station.core.bootstrap.utils.MyObjectUtils;

import java.time.LocalDateTime;
import java.util.List;

@Builder
public record LoginLogCriteria(
        Long accountId,
        String search,
        List<LocalDateTime> timeRange,
        List<String> logTypeCodes
) {
    public LoginLogCriteria {
        search = MyObjectUtils.defaultValue(search);
        timeRange = MyDateTimeUtils.defaultTimeRange(timeRange);
        logTypeCodes = MyObjectUtils.defaultValue(logTypeCodes);
    }

    public static LoginLogCriteria of(
            Long accountId,
            String search,
            List<LocalDateTime> timeRange,
            List<String> logTypeCodes
    ) {
        return LoginLogCriteria.builder()
                .accountId(accountId)
                .search(search)
                .timeRange(timeRange)
                .logTypeCodes(logTypeCodes)
                .build();
    }
}
