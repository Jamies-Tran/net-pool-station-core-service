package net.pool.station.core.domain.station.resource;

import com.fasterxml.jackson.core.type.TypeReference;
import lombok.Builder;
import net.pool.station.core.bootstrap.enums.EResourceStatus;
import net.pool.station.core.bootstrap.enums.ERole;
import net.pool.station.core.bootstrap.utils.MyObjectUtils;
import net.pool.station.core.bootstrap.utils.MyRequestContext;
import net.pool.station.core.domain.login.info.LoginInfo;

import java.util.List;

@Builder
public record StationResourceCriteria(
        String search,
        Long areaId,
        List<String> typeCodes,
        List<String> statusCodes
) {
    public StationResourceCriteria {
        areaId = MyObjectUtils.defaultValue(areaId, new TypeReference<>() {});
        search = MyObjectUtils.defaultValue(search, new TypeReference<>() {});
        typeCodes = MyObjectUtils.defaultValue(typeCodes, new TypeReference<>() {});
        statusCodes = authorizedStatusCodes();
    }

    public static StationResourceCriteria of(
            String search,
            Long areaId,
            List<String> typeCodes,
            List<String> statusCodes
    ) {
        return StationResourceCriteria.builder()
                .search(search)
                .areaId(areaId)
                .typeCodes(typeCodes)
                .statusCodes(statusCodes)
                .build();
    }

    private List<String> authorizedStatusCodes() {
        LoginInfo loginInfo = MyRequestContext.currentLoginInfo()
                .orElse(LoginInfo.currentLoginInfoEmpty());
        List<String> authorizedStatus = MyObjectUtils.defaultValue(statusCodes, new TypeReference<>() {});
        if (loginInfo.isLoginEmpty()
                || MyObjectUtils.isEquals(ERole.PLAYER.getCode(), loginInfo.roleCode())) {
            authorizedStatus.removeIf(s -> MyObjectUtils.isNotEquals(EResourceStatus.ENABLE.getCode(), s));
        }

        return authorizedStatus;
    }
}
