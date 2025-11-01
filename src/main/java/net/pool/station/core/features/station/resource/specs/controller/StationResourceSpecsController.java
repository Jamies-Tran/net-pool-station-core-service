package net.pool.station.core.features.station.resource.specs.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.pool.station.core.bootstrap.enums.ESpecType;
import net.pool.station.core.bootstrap.rest.response.MyValueResponse;
import net.pool.station.core.domain.station.resource.specs.StationResourceSpec;
import net.pool.station.core.domain.station.resource.specs.StationResourceSpecUseCase;
import net.pool.station.core.features.station.resource.specs.controller.models.bt.BilliardTableSpecRequest;
import net.pool.station.core.features.station.resource.specs.controller.models.bt.BilliardTableSpecRequestMapper;
import net.pool.station.core.features.station.resource.specs.controller.models.cs.ConsoleSpecRequest;
import net.pool.station.core.features.station.resource.specs.controller.models.cs.ConsoleSpecRequestMapper;
import net.pool.station.core.features.station.resource.specs.controller.models.pc.PCSpecsRequest;
import net.pool.station.core.features.station.resource.specs.controller.models.pc.PCSpecsRequestMapper;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class StationResourceSpecsController implements StationResourceSpecApi {
    StationResourceSpecUseCase stationResourceSpecUseCase;

    PCSpecsRequestMapper pcRequestMapper;

    BilliardTableSpecRequestMapper btSpecRequestMapper;

    ConsoleSpecRequestMapper csSpecRequestMapper;

    @Override
    public MyValueResponse<?> saveForPc(Long stationResourceId, PCSpecsRequest request) {
        StationResourceSpec spec = pcRequestMapper.toDto(request);
        spec = spec.withStationResourceId(stationResourceId)
                .withTypeCode(ESpecType.PC.getCode())
                .withTypeName(ESpecType.PC.getName());
        stationResourceSpecUseCase.save(spec);

        return MyValueResponse.successNoData();
    }

    @Override
    public MyValueResponse<?> saveForBilliardTable(Long stationResourceId, BilliardTableSpecRequest request) {
        StationResourceSpec spec = btSpecRequestMapper.toDto(request);
        spec = spec.withStationResourceId(stationResourceId)
                .withTypeCode(ESpecType.Billiard_TABLE.getCode())
                .withTypeName(ESpecType.Billiard_TABLE.getName());
        stationResourceSpecUseCase.save(spec);

        return MyValueResponse.successNoData();
    }

    @Override
    public MyValueResponse<?> saveForConsole(Long stationResourceId, ConsoleSpecRequest request) {
        StationResourceSpec spec = csSpecRequestMapper.toDto(request);
        spec = spec.withStationResourceId(stationResourceId)
                .withTypeCode(ESpecType.CONSOLE.getCode())
                .withTypeName(ESpecType.CONSOLE.getName());
        stationResourceSpecUseCase.save(spec);

        return MyValueResponse.successNoData();
    }
}
