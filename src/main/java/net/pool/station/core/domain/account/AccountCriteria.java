package net.pool.station.core.domain.account;

import lombok.Builder;
import net.pool.station.core.bootstrap.enums.EAccountStatus;
import net.pool.station.core.bootstrap.utils.MyDateTimeUtils;
import net.pool.station.core.bootstrap.utils.MyObjectUtils;
import net.pool.station.core.bootstrap.utils.MyRequestContext;
import net.pool.station.core.domain.login.info.LoginInfo;

import java.time.LocalDateTime;
import java.util.ArrayList;
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
        statusCodes = authorizedStatusCodes(statusCodes);
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

    private List<String> authorizedStatusCodes(List<String> statusCodes) {
        LoginInfo currentLoginInfo = MyRequestContext.currentLoginInfo()
                .orElse(LoginInfo.currentLoginInfoEmpty());
        List<String> authorizedStatusCodes = Optional.ofNullable(statusCodes)
                .orElse(new ArrayList<>());
        if (currentLoginInfo.isLoginEmpty()) {
            authorizedStatusCodes.remove(EAccountStatus.DISABLE.getCode());
        }

        return authorizedStatusCodes;
    }
}
