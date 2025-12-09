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
        List<Double> distances
) {
    public StationCriteria {
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
                .gameName(gameName)
                .pcCpu(pcCpu)
                .pcRam(pcRam)
                .pcGpu(pcGpu)
                .pcMonitor(pcMonitor)
                .pcKeyboard(pcKeyboard)
                .pcMouse(pcMouse)
                .pcHeadphone(pcHeadphone)
                .btTableDetail(btTableDetail)
                .btCueDetail(btCueDetail)
                .btBallDetail(btBallDetail)
                .csConsoleModel(csConsoleModel)
                .csTvModel(csTvModel)
                .csControllerType(csControllerType)
                .csControllerCount(csControllerCount)
                .statusCodes(statusCodes)
                .distances(distances)
                .build();
    }
}
