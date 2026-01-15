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
        Double latitude,
        Double longitude,
        String gameName,
        String pcCpu,
        String pcRam,
        String pcGpu,
        String pcMonitor,
        String pcKeyboard,
        String pcMouse,
        String pcHeadphone,
        String btTableDetail,
        String btCueDetail,
        String btBallDetail,
        String csConsoleModel,
        String csTvModel,
        String csControllerType,
        Integer csControllerCount,
        List<LocalDateTime> timeRange,
        List<String> statusCodes,
        String createdBy
) {
    public StationCriteria {
        createdBy = MyObjectUtils.defaultValue(createdBy, new TypeReference<>() {});
        search = MyObjectUtils.defaultValue(search, new TypeReference<>() {});
        province = MyObjectUtils.defaultValue(province, new TypeReference<>() {});
        commune = MyObjectUtils.defaultValue(commune, new TypeReference<>() {});
        district = MyObjectUtils.defaultValue(district, new TypeReference<>() {});
        statusCodes = authorizeStatusCodes(statusCodes);
        gameName = MyObjectUtils.defaultValue(gameName, new TypeReference<>() {});
        pcCpu = MyObjectUtils.defaultValue(pcCpu, new TypeReference<>() {});
        pcRam = MyObjectUtils.defaultValue(pcRam, new TypeReference<>() {});
        pcGpu = MyObjectUtils.defaultValue(pcGpu, new TypeReference<>() {});
        pcMonitor = MyObjectUtils.defaultValue(pcMonitor, new TypeReference<>() {});
        pcKeyboard = MyObjectUtils.defaultValue(pcKeyboard, new TypeReference<>() {});
        pcMouse = MyObjectUtils.defaultValue(pcMouse, new TypeReference<>() {});
        pcHeadphone = MyObjectUtils.defaultValue(pcHeadphone, new TypeReference<>() {});
        btTableDetail = MyObjectUtils.defaultValue(btTableDetail, new TypeReference<>() {});
        btCueDetail = MyObjectUtils.defaultValue(btCueDetail, new TypeReference<>() {});
        btBallDetail = MyObjectUtils.defaultValue(btBallDetail, new TypeReference<>() {});
        csConsoleModel = MyObjectUtils.defaultValue(csConsoleModel, new TypeReference<>() {});
        csTvModel = MyObjectUtils.defaultValue(csTvModel, new TypeReference<>() {});
        csControllerType = MyObjectUtils.defaultValue(csControllerType, new TypeReference<>() {});
        csControllerCount = MyObjectUtils.defaultValue(csControllerCount, new TypeReference<>() {});
        latitude = MyObjectUtils.defaultValue(latitude, new TypeReference<>() {});
        longitude = MyObjectUtils.defaultValue(longitude, new TypeReference<>() {});
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
                                     String createdBy,
                                     String province,
                                     String commune,
                                     String district,
                                     Double latitude,
                                     Double longitude,
                                     String gameName,
                                     List<String> statusCodes) {
        return StationCriteria.builder()
                .search(search)
                .createdBy(createdBy)
                .province(province)
                .commune(commune)
                .district(district)
                .gameName(gameName)
                .statusCodes(statusCodes)
                .latitude(latitude)
                .longitude(longitude)
                .build();
    }
}
