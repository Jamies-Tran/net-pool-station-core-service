package net.pool.station.core.domain.station.resource.specs;

import com.fasterxml.jackson.core.type.TypeReference;
import lombok.Builder;
import net.pool.station.core.bootstrap.utils.MyObjectUtils;

@Builder
public record StationResourceSpecCriteria(
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
        String typeCode
) {
    public StationResourceSpecCriteria {
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
    }
}
