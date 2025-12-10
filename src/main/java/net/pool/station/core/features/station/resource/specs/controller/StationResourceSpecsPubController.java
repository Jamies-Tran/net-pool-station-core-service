package net.pool.station.core.features.station.resource.specs.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.pool.station.core.bootstrap.rest.response.MyListResponse;
import net.pool.station.core.domain.station.resource.specs.StationResourceSpec;
import net.pool.station.core.domain.station.resource.specs.StationResourceSpecCriteria;
import net.pool.station.core.domain.station.resource.specs.StationResourceSpecUseCase;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class StationResourceSpecsPubController implements StationResourceSpecsPubApi {
    StationResourceSpecUseCase stationResourceSpecUseCase;


    @Override
    public MyListResponse<String> findAllPcCpu(String search) {
        StationResourceSpecCriteria criteria = StationResourceSpecCriteria.builder()
                .pcCpu(search)
                .typeCode("PC_CPU")
                .build();

        return MyListResponse.success(stationResourceSpecUseCase.findAll(criteria));
    }

    @Override
    public MyListResponse<String> findAllPcRam(String search) {
        StationResourceSpecCriteria criteria = StationResourceSpecCriteria.builder()
                .pcRam(search)
                .typeCode("PC_RAM")
                .build();

        return MyListResponse.success(stationResourceSpecUseCase.findAll(criteria));
    }

    @Override
    public MyListResponse<String> findAllPcGpu(String search) {
        StationResourceSpecCriteria criteria = StationResourceSpecCriteria.builder()
                .pcGpu(search)
                .typeCode("PC_GPU")
                .build();

        return MyListResponse.success(stationResourceSpecUseCase.findAll(criteria));
    }

    @Override
    public MyListResponse<String> findAllPcMonitor(String search) {
        StationResourceSpecCriteria criteria = StationResourceSpecCriteria.builder()
                .pcMonitor(search)
                .typeCode("PC_MONITOR")
                .build();

        return MyListResponse.success(stationResourceSpecUseCase.findAll(criteria));
    }

    @Override
    public MyListResponse<String> findAllPcKeyboard(String search) {
        StationResourceSpecCriteria criteria = StationResourceSpecCriteria.builder()
                .pcKeyboard(search)
                .typeCode("PC_KEYBOARD")
                .build();

        return MyListResponse.success(stationResourceSpecUseCase.findAll(criteria));
    }

    @Override
    public MyListResponse<String> findAllPcMouse(String search) {
        StationResourceSpecCriteria criteria = StationResourceSpecCriteria.builder()
                .pcMouse(search)
                .typeCode("PC_MOUSE")
                .build();

        return MyListResponse.success(stationResourceSpecUseCase.findAll(criteria));
    }

    @Override
    public MyListResponse<String> findAllPcHeadphone(String search) {
        StationResourceSpecCriteria criteria = StationResourceSpecCriteria.builder()
                .pcHeadphone(search)
                .typeCode("PC_HEADPHONE")
                .build();

        return MyListResponse.success(stationResourceSpecUseCase.findAll(criteria));
    }

    @Override
    public MyListResponse<String> findAllBtTableDetail(String search) {
        StationResourceSpecCriteria criteria = StationResourceSpecCriteria.builder()
                .btTableDetail(search)
                .typeCode("BT_TABLE_DETAIL")
                .build();

        return MyListResponse.success(stationResourceSpecUseCase.findAll(criteria));
    }

    @Override
    public MyListResponse<String> findAllBtCueDetail(String search) {
        StationResourceSpecCriteria criteria = StationResourceSpecCriteria.builder()
                .btCueDetail(search)
                .typeCode("BT_CUE_DETAIL")
                .build();

        return MyListResponse.success(stationResourceSpecUseCase.findAll(criteria));
    }

    @Override
    public MyListResponse<String> findAllBtBallDetail(String search) {
        StationResourceSpecCriteria criteria = StationResourceSpecCriteria.builder()
                .btBallDetail(search)
                .typeCode("BT_BALL_DETAIL")
                .build();

        return MyListResponse.success(stationResourceSpecUseCase.findAll(criteria));
    }

    @Override
    public MyListResponse<String> findAllCsConsoleModel(String search) {
        StationResourceSpecCriteria criteria = StationResourceSpecCriteria.builder()
                .csConsoleModel(search)
                .typeCode("CS_CONSOLE_MODEL")
                .build();

        return MyListResponse.success(stationResourceSpecUseCase.findAll(criteria));
    }

    @Override
    public MyListResponse<String> findAllCsTvModel(String search) {
        StationResourceSpecCriteria criteria = StationResourceSpecCriteria.builder()
                .csTvModel(search)
                .typeCode("CS_TV_MODEL")
                .build();

        return MyListResponse.success(stationResourceSpecUseCase.findAll(criteria));
    }

    @Override
    public MyListResponse<String> findAllControllerType(String search) {
        StationResourceSpecCriteria criteria = StationResourceSpecCriteria.builder()
                .csControllerType(search)
                .typeCode("CS_CONTROLLER_TYPE")
                .build();

        return MyListResponse.success(stationResourceSpecUseCase.findAll(criteria));
    }

    @Override
    public MyListResponse<String> findAllCsControllerCount(String search) {
        StationResourceSpecCriteria criteria = StationResourceSpecCriteria.builder()
                .csControllerCount(Integer.valueOf(search))
                .typeCode("CS_CONTROLLER_COUNT")
                .build();

        return MyListResponse.success(stationResourceSpecUseCase.findAll(criteria));
    }
}
