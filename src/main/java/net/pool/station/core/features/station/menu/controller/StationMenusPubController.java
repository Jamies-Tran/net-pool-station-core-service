package net.pool.station.core.features.station.menu.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.pool.station.core.bootstrap.rest.response.MyPageResponse;
import net.pool.station.core.bootstrap.rest.response.MySorter;
import net.pool.station.core.domain.station.menu.StationMenuCriteria;
import net.pool.station.core.domain.station.menu.StationMenuUseCase;
import net.pool.station.core.features.station.menu.controller.models.StationMenuResponse;
import net.pool.station.core.features.station.menu.controller.models.StationMenuResponseMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class StationMenusPubController implements StationMenusPubApi {
    StationMenuUseCase stationMenuUseCase;

    StationMenuResponseMapper responseMapper;

    @Override
    public MyPageResponse<StationMenuResponse> findAll(
            String search,
            Long stationId,
            List<Long> priceRange,
            List<String> typeCodes,
            List<String> statusCodes,
            String sorter, Integer current, Integer pageSize
    ) {
        StationMenuCriteria criteria = StationMenuCriteria.of(search, stationId, priceRange, statusCodes, typeCodes);
        PageRequest pageRequest = PageRequest.of(current, pageSize, MySorter.of(sorter));
        Page<StationMenuResponse> responses = stationMenuUseCase.findAll(criteria, pageRequest)
                .map(responseMapper::toModel);

        return MyPageResponse.success(responses);
    }
}
