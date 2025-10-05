package net.pool.station.core.domain.timeslot;

import com.fasterxml.jackson.core.type.TypeReference;
import lombok.Builder;
import net.pool.station.core.bootstrap.enums.ERole;
import net.pool.station.core.bootstrap.enums.ETimeSlotStatus;
import net.pool.station.core.bootstrap.utils.MyObjectUtils;
import net.pool.station.core.bootstrap.utils.MyRequestContext;
import net.pool.station.core.domain.login.info.LoginInfo;

import java.util.List;

@Builder
public record TimeSlotCriteria(
        Long scheduleId,
        List<String> periodCodes,
        List<String> statusCodes
) {
    public TimeSlotCriteria {
        periodCodes = MyObjectUtils.defaultValue(periodCodes, new TypeReference<>() {});
        statusCodes = authorizeStatusCodes();
    }

    public static TimeSlotCriteria of(Long scheduleId, List<String> periodCodes, List<String> statusCodes) {
        return TimeSlotCriteria.builder()
                .scheduleId(scheduleId)
                .periodCodes(periodCodes)
                .statusCodes(statusCodes)
                .build();
    }

    private List<String> authorizeStatusCodes() {
        LoginInfo currentLoginInfo = MyRequestContext.currentLoginInfo()
                .orElse(LoginInfo.currentLoginInfoEmpty());
        if (currentLoginInfo.isLoginEmpty() || !List.of(ERole.STATION_ADMIN.getCode(), ERole.STATION_OWNER.getCode())
                .contains(currentLoginInfo.roleCode())) {
            return List.of(ETimeSlotStatus.ENABLED.getCode());
        }

        return MyObjectUtils.defaultValue(statusCodes, new TypeReference<>() {});
    }
}
