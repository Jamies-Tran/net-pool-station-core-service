package net.pool.station.core.domain.account.log;

import lombok.Builder;
import net.pool.station.core.bootstrap.utils.MyDateTimeUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Builder
public record AccountLogCriteria(
        String search,
        Long accountId,
        List<LocalDateTime> timeRange,
        List<String> actionCodes
) {
    public AccountLogCriteria {
        search = Optional.ofNullable(search).orElse("");
        timeRange = MyDateTimeUtils.defaultTimeRange(timeRange);
        actionCodes = Optional.ofNullable(actionCodes).orElse(List.of());
    }

    public static AccountLogCriteria of(
            String search,
            Long accountId,
            List<LocalDateTime> timeRange,
            List<String> actionCodes
    ) {
       return AccountLogCriteria.builder()
               .search(search)
               .accountId(accountId)
               .timeRange(timeRange)
               .actionCodes(actionCodes)
               .build();
    }
}
