package net.pool.station.core.features.area.type.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.pool.station.core.bootstrap.configuration.handler.exception.MyResourceNotFoundException;
import net.pool.station.core.bootstrap.rest.response.MyValueResponse;
import net.pool.station.core.domain.DomainKey;
import net.pool.station.core.domain.area.type.AreaTypeUseCase;
import net.pool.station.core.features.area.type.controller.models.AreaTypeRequestMapper;
import net.pool.station.core.features.area.type.controller.models.AreaTypeResponse;
import net.pool.station.core.features.area.type.controller.models.AreaTypeResponseMapper;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AreaTypeController implements AreaTypeApi {
    AreaTypeUseCase areaTypeUseCase;

    AreaTypeResponseMapper responseMapper;

    AreaTypeRequestMapper requestMapper;

    @Override
    public MyValueResponse<AreaTypeResponse> findById(Long areaTypeId) {
        AreaTypeResponse response = areaTypeUseCase.findById(DomainKey.of(areaTypeId))
                .map(responseMapper::toModel)
                .orElseThrow(MyResourceNotFoundException::new);

        return MyValueResponse.success(response);
    }

    @Override
    public MyValueResponse<?> enable(Long areaTypeId) {
        areaTypeUseCase.active(DomainKey.of(areaTypeId));

        return MyValueResponse.successNoData();
    }

    @Override
    public MyValueResponse<?> disable(Long areaTypeId) {
        areaTypeUseCase.inactive(DomainKey.of(areaTypeId));

        return MyValueResponse.successNoData();
    }

    @Override
    public MyValueResponse<?> delete(Long areaTypeId) {
        areaTypeUseCase.delete(DomainKey.of(areaTypeId));

        return MyValueResponse.successNoData();
    }
}
