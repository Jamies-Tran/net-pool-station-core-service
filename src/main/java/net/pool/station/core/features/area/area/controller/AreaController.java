package net.pool.station.core.features.area.area.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.pool.station.core.bootstrap.rest.response.MyValueResponse;
import net.pool.station.core.domain.DomainKey;
import net.pool.station.core.domain.area.AreaUseCase;
import net.pool.station.core.features.area.area.controller.models.AreaRequest;
import net.pool.station.core.features.area.area.controller.models.AreaRequestMapper;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AreaController implements AreaApi {
    AreaUseCase areaUseCase;

    AreaRequestMapper requestMapper;

    @Override
    public MyValueResponse<?> update(Long areaId, AreaRequest request) {
        areaUseCase.update(DomainKey.of(areaId), requestMapper.toDto(request));

        return MyValueResponse.successNoData();
    }

    @Override
    public MyValueResponse<?> enable(Long areaId) {
        areaUseCase.enable(DomainKey.of(areaId));

        return MyValueResponse.successNoData();
    }

    @Override
    public MyValueResponse<?> disable(Long areaId) {
        areaUseCase.disable(DomainKey.of(areaId));

        return MyValueResponse.successNoData();
    }

    @Override
    public MyValueResponse<?> delete(Long areaId) {
        areaUseCase.delete(DomainKey.of(areaId));

        return MyValueResponse.successNoData();
    }
}
