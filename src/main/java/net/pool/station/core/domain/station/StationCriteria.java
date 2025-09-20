package net.pool.station.core.domain.station;

import com.fasterxml.jackson.core.type.TypeReference;
import lombok.Builder;
import net.pool.station.core.bootstrap.enums.ERole;
import net.pool.station.core.bootstrap.enums.EStationStatus;
import net.pool.station.core.bootstrap.utils.MyDateTimeUtils;
import net.pool.station.core.bootstrap.utils.MyObjectUtils;
import net.pool.station.core.bootstrap.utils.MyRequestContext;
import net.pool.station.core.domain.login.info.LoginInfo;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Builder
public record StationCriteria(
        String search,
        String province,
        String commune,
        String district,
        List<LocalDateTime> timeRange,
        List<String> statusCodes,
        List<Double> distances
) {
    public StationCriteria {
        search = MyObjectUtils.defaultValue(search, new TypeReference<>() {});
        province = MyObjectUtils.defaultValue(province, new TypeReference<>() {});
        commune = MyObjectUtils.defaultValue(commune, new TypeReference<>() {});
        district = MyObjectUtils.defaultValue(district, new TypeReference<>() {});
        timeRange = MyDateTimeUtils.defaultTimeRange(timeRange);
        statusCodes = authorizeStatusCodes(statusCodes);
        distances = MyObjectUtils.defaultValue(distances, new TypeReference<>() {});
    }

    private List<String> authorizeStatusCodes(List<String> statusCodes) {
        statusCodes = MyObjectUtils.defaultValue(statusCodes, new TypeReference<>() {});
        Optional<LoginInfo> currentLoginInfo = MyRequestContext.currentLoginInfo();

        if (currentLoginInfo.isPresent()) {
            LoginInfo loginInfo = currentLoginInfo.get();
            if (MyObjectUtils.isEquals(ERole.PLAYER.getCode(), loginInfo.roleCode())) {
                statusCodes = List.of(EStationStatus.ACTIVE.getCode());
            }
        }

        return statusCodes;
    }

    public static StationCriteria of(String search,
                                     String province,
                                     String commune,
                                     String district,
                                     Double distance,
                                     List<LocalDateTime> timeRange,
                                     List<String> statusCodes) {
        List<Double> distances = new ArrayList<>();
        if (MyObjectUtils.isNotEmpty(distance)) {
            distances.addAll(List.of(0.0, distance));
        }

        return StationCriteria.builder()
                .search(search)
                .province(province)
                .commune(commune)
                .district(district)
                .timeRange(timeRange)
                .statusCodes(statusCodes)
                .distances(distances)
                .build();
    }
}
