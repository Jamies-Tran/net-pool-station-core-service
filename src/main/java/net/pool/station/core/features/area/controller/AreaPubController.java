package net.pool.station.core.features.area.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.pool.station.core.bootstrap.configuration.handler.exception.MyResourceNotFoundException;
import net.pool.station.core.bootstrap.rest.response.MyValueResponse;
import net.pool.station.core.domain.DomainKey;
import net.pool.station.core.domain.area.AreaUseCase;
import net.pool.station.core.features.area.controller.models.AreaResponse;
import net.pool.station.core.features.area.controller.models.AreaResponseMapper;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AreaPubController implements AreaPubApi {
    AreaUseCase areaUseCase;

    AreaResponseMapper responseMapper;

    @Override
    public MyValueResponse<AreaResponse> findById(Long areaId) {
        AreaResponse response = areaUseCase.findById(DomainKey.of(areaId))
                .map(responseMapper::toModel)
                .orElseThrow(MyResourceNotFoundException::new);

        return MyValueResponse.success(response);
    }
}
