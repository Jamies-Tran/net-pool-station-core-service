package net.pool.station.core.domain.booking;

import com.fasterxml.jackson.core.type.TypeReference;
import lombok.Builder;
import net.pool.station.core.bootstrap.enums.EBookingStatus;
import net.pool.station.core.bootstrap.enums.ERole;
import net.pool.station.core.bootstrap.utils.MyDateTimeUtils;
import net.pool.station.core.bootstrap.utils.MyObjectUtils;
import net.pool.station.core.bootstrap.utils.MyRequestContext;
import net.pool.station.core.domain.login.info.LoginInfo;

import java.time.LocalDate;
import java.util.List;

@Builder
public record BookingCriteria(
        String search,
        Long accountId,
        List<LocalDate> dateRange,
        List<String> typeCodes,
        List<String> statusCodes
) {
    public BookingCriteria {
        LoginInfo currentLoginInfo = MyRequestContext.currentLoginInfo()
                .orElse(LoginInfo.currentLoginInfoEmpty());
        if (!currentLoginInfo.isLoginEmpty()
                && MyObjectUtils.isEquals(ERole.PLAYER.getCode(), currentLoginInfo.roleCode())) {
           accountId = currentLoginInfo.accountId();
        } else {
            accountId = MyObjectUtils.defaultValue(accountId, new TypeReference<>() {});
        }
        search = MyObjectUtils.defaultValue(search, new TypeReference<>() {});
        dateRange = MyObjectUtils.defaultValue(dateRange, new TypeReference<>() {});
        typeCodes = MyObjectUtils.defaultValue(typeCodes, new TypeReference<>() {});
        statusCodes = MyObjectUtils.defaultValue(statusCodes, new TypeReference<>() {});
    }

    public static BookingCriteria of(
            String search,
            Long accountId,
            List<LocalDate> dateRange,
            List<String> typeCodes,
            List<String> statusCodes
    ) {
        return BookingCriteria.builder()
                .search(search)
                .accountId(accountId)
                .dateRange(dateRange)
                .typeCodes(typeCodes)
                .statusCodes(statusCodes)
                .build();
    }



    public LocalDate startFrom() {
        if (MyObjectUtils.isEmpty(dateRange) || dateRange.size() < 2) {
            return null;
        }

        return dateRange.getFirst();
    }

    public LocalDate endTo() {
        if (MyObjectUtils.isEmpty(dateRange) || dateRange.size() < 2) {
            return null;
        }

        return dateRange.get(1);
    }
}
