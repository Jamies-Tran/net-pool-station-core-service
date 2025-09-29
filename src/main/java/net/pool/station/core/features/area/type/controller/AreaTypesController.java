package net.pool.station.core.features.area.type.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.pool.station.core.bootstrap.rest.response.MyPageResponse;
import net.pool.station.core.bootstrap.rest.response.MySorter;
import net.pool.station.core.bootstrap.rest.response.MyValueResponse;
import net.pool.station.core.domain.area.type.AreaTypeCriteria;
import net.pool.station.core.domain.area.type.AreaTypeUseCase;
import net.pool.station.core.features.area.type.controller.models.AreaTypeRequest;
import net.pool.station.core.features.area.type.controller.models.AreaTypeRequestMapper;
import net.pool.station.core.features.area.type.controller.models.AreaTypeResponse;
import net.pool.station.core.features.area.type.controller.models.AreaTypeResponseMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AreaTypesController implements AreaTypesApi {
    AreaTypeUseCase areaTypeUseCase;

    AreaTypeRequestMapper requestMapper;

    AreaTypeResponseMapper responseMapper;

    @Override
    public MyValueResponse<?> save(AreaTypeRequest request) {
        areaTypeUseCase.save(requestMapper.toDto(request));

        return MyValueResponse.successNoData();
    }

    @Override
    public MyPageResponse<AreaTypeResponse> findAll(
            String search,
            List<String> typeCodes,
            List<String> statusCodes,
            String sorter,
            Integer current,
            Integer pageSize
    ) {
        AreaTypeCriteria criteria = AreaTypeCriteria.of(search, typeCodes, statusCodes);
        PageRequest pageRequest = PageRequest.of(current, pageSize, MySorter.of(sorter));
        Page<AreaTypeResponse> responses = areaTypeUseCase.findAll(criteria, pageRequest)
                .map(responseMapper::toModel);

        return MyPageResponse.success(responses);
    }
}
