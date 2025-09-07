package net.pool.station.core.domain.account;

import lombok.Builder;
import net.pool.station.core.bootstrap.utils.MyDateTimeUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Builder
public record AccountCriteria(
        List<LocalDateTime> timeRange,
        String search,
        List<String> statusCodes,
        List<Long> roleIds
) {
    public AccountCriteria {
        timeRange = MyDateTimeUtils.defaultTimeRange(timeRange);
        search = Optional.ofNullable(search).orElse("");
        statusCodes = Optional.ofNullable(statusCodes).orElse(List.of());
        roleIds = Optional.ofNullable(roleIds).orElse(List.of());
    }

    public static AccountCriteria of(
            String search,
            List<LocalDateTime> timeRange,
            List<String> statusCodes,
            List<Long> roleIds
    ) {
        return AccountCriteria.builder()
                .search(search)
                .timeRange(timeRange)
                .statusCodes(statusCodes)
                .roleIds(roleIds)
                .build();
    }
}
