package net.pool.station.core.domain.account;

import com.fasterxml.jackson.core.type.TypeReference;
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
        String search,
        Long stationId,
        List<LocalDateTime> timeRange,
        List<String> statusCodes,
        List<Long> roleIds
) {
    public AccountCriteria {
        stationId = MyObjectUtils.defaultValue(stationId, new TypeReference<>() {});
        timeRange = MyDateTimeUtils.defaultTimeRange(timeRange);
        search = MyObjectUtils.defaultValue(search, new TypeReference<>() {});
        statusCodes = authorizedStatusCodes(statusCodes);
        roleIds = MyObjectUtils.defaultValue(roleIds, new TypeReference<>() {});
    }

    public static AccountCriteria of(
            String search,
            Long stationId,
            List<LocalDateTime> timeRange,
            List<String> statusCodes,
            List<Long> roleIds
    ) {
        return AccountCriteria.builder()
                .search(search)
                .stationId(stationId)
                .timeRange(timeRange)
                .statusCodes(statusCodes)
                .roleIds(roleIds)
                .build();
    }

    private List<String> authorizedStatusCodes(List<String> statusCodes) {
        LoginInfo currentLoginInfo = MyRequestContext.currentLoginInfo()
                .orElse(LoginInfo.currentLoginInfoEmpty());
        List<String> authorizedStatusCodes = MyObjectUtils.defaultValue(statusCodes, new TypeReference<>() {});
        if (currentLoginInfo.isLoginEmpty()) {
            authorizedStatusCodes.remove(EAccountStatus.DISABLE.getCode());
        }

        return authorizedStatusCodes;
    }
}
