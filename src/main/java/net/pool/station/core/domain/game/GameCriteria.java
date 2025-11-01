package net.pool.station.core.domain.game;

import com.fasterxml.jackson.core.type.TypeReference;
import lombok.Builder;
import net.pool.station.core.bootstrap.enums.EGameStatus;
import net.pool.station.core.bootstrap.enums.ERole;
import net.pool.station.core.bootstrap.utils.MyObjectUtils;
import net.pool.station.core.bootstrap.utils.MyRequestContext;
import net.pool.station.core.domain.login.info.LoginInfo;

import java.util.List;

@Builder
public record GameCriteria(
        String search,
        Long stationSpaceId,
        List<String> genreCodes,
        List<String> statusCodes
) {
    public GameCriteria {
        search = MyObjectUtils.defaultValue(search, new TypeReference<>() {});
        genreCodes = MyObjectUtils.defaultValue(genreCodes, new TypeReference<>() {});
        statusCodes = authorizeStatusCodes(statusCodes);
    }

    public static GameCriteria of(String search, Long stationSpaceId, List<String> genreCodes, List<String> statusCodes) {
        return GameCriteria.builder()
                .search(search)
                .stationSpaceId(stationSpaceId)
                .genreCodes(genreCodes)
                .statusCodes(statusCodes)
                .build();
    }

    private List<String> authorizeStatusCodes(List<String> statusCodes) {
        statusCodes = MyObjectUtils.defaultValue(statusCodes, new TypeReference<List>() {});

        LoginInfo currentLoginInfo = MyRequestContext.currentLoginInfo()
                .orElse(LoginInfo.currentLoginInfoEmpty());
        if (!currentLoginInfo.isLoginEmpty()
                && MyObjectUtils.isEquals(currentLoginInfo.roleCode(), ERole.PLAYER.getCode())) {
            statusCodes
                    .removeIf(status -> MyObjectUtils.isNotEquals(EGameStatus.ENABLE.getCode(), status));
        }

        return MyObjectUtils.defaultValue(statusCodes, new TypeReference<List>() {});
    }
}
