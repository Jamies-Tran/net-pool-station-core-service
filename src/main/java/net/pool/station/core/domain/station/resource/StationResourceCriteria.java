package net.pool.station.core.domain.station.resource;

import com.fasterxml.jackson.core.type.TypeReference;
import lombok.Builder;
import net.pool.station.core.bootstrap.enums.EResourceStatus;
import net.pool.station.core.bootstrap.enums.ERole;
import net.pool.station.core.bootstrap.utils.MyObjectUtils;
import net.pool.station.core.bootstrap.utils.MyRequestContext;
import net.pool.station.core.domain.login.info.LoginInfo;

import java.time.LocalDateTime;
import java.util.List;

@Builder
public record StationResourceCriteria(
        String search,
        Long stationSpaceId,
        Long areaId,
        List<LocalDateTime> timeRange,
        List<String> typeCodes,
        List<String> statusCodes
) {
    public StationResourceCriteria {
        stationSpaceId = MyObjectUtils.defaultValue(stationSpaceId, new TypeReference<>() {});
        areaId = MyObjectUtils.defaultValue(areaId, new TypeReference<>() {});
        search = MyObjectUtils.defaultValue(search, new TypeReference<>() {});
        typeCodes = MyObjectUtils.defaultValue(typeCodes, new TypeReference<>() {});
        statusCodes = authorizedStatusCodes(statusCodes);
    }

    public static StationResourceCriteria of(
            String search,
            Long stationSpaceId,
            Long areaId,
            List<String> typeCodes,
            List<String> statusCodes
    ) {
        return StationResourceCriteria.builder()
                .search(search)
                .stationSpaceId(stationSpaceId)
                .areaId(areaId)
                .typeCodes(typeCodes)
                .statusCodes(statusCodes)
                .build();
    }

    private List<String> authorizedStatusCodes(List<String> statusCodes) {
        List<String> authorizedStatus = MyObjectUtils.defaultValue(statusCodes, new TypeReference<List>() {});

        LoginInfo loginInfo = MyRequestContext.currentLoginInfo()
                .orElse(LoginInfo.currentLoginInfoEmpty());
        if (loginInfo.isLoginEmpty()
                || MyObjectUtils.isEquals(ERole.PLAYER.getCode(), loginInfo.roleCode())) {
            authorizedStatus = List.of(EResourceStatus.ENABLE.getCode());
        }

        return authorizedStatus;
    }
}
