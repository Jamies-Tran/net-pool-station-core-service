package net.pool.station.core.domain.booking;

import com.fasterxml.jackson.core.type.TypeReference;
import net.pool.station.core.bootstrap.utils.MyDateTimeUtils;
import net.pool.station.core.bootstrap.utils.MyObjectUtils;

import java.time.LocalDate;
import java.util.List;

public record BookingCriteria(
        String search,
        Long accountId,
        List<LocalDate> dateRange,
        List<String> typeCodes,
        List<String> statusCodes
) {
    public BookingCriteria {
        search = MyObjectUtils.defaultValue(search, new TypeReference<>() {});
        accountId = MyObjectUtils.defaultValue(accountId, new TypeReference<>() {});
        dateRange = MyObjectUtils.defaultValue(dateRange, new TypeReference<>() {});
        typeCodes = MyObjectUtils.defaultValue(typeCodes, new TypeReference<>() {});
        statusCodes = MyObjectUtils.defaultValue(statusCodes, new TypeReference<>() {});
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
