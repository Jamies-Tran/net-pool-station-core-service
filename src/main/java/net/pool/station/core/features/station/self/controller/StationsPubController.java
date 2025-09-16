package net.pool.station.core.features.station.self.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.pool.station.core.bootstrap.rest.response.MyPageResponse;
import net.pool.station.core.bootstrap.rest.response.MySorter;
import net.pool.station.core.domain.station.StationCriteria;
import net.pool.station.core.domain.station.StationUseCase;
import net.pool.station.core.features.station.self.controller.models.StationResponse;
import net.pool.station.core.features.station.self.controller.models.StationResponseMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class StationsPubController implements StationsPubApi {
    StationUseCase stationUseCase;

    StationResponseMapper responseMapper;

    @Override
    public MyPageResponse<StationResponse> findAll(
            String search,
            String province,
            String commune,
            String district,
            Double distance,
            List<LocalDateTime> timeRange,
            List<String> statusCodes,
            String sorter, Integer current, Integer pageSize)
    {
        StationCriteria criteria = StationCriteria.of(search, province, commune, district, distance, timeRange,
                statusCodes);
        PageRequest pageRequest = PageRequest.of(current, pageSize, MySorter.of(sorter));
        Page<StationResponse> responses = stationUseCase.findAll(criteria, pageRequest)
                .map(responseMapper::toModel);

        return MyPageResponse.success(responses);
    }

    @Override
    public MyPageResponse<String> findAllStationProvince(
            String province,
            String sorter,
            Integer current,
            Integer pageSize
    ) {
        PageRequest pageRequest = PageRequest.of(current, pageSize, MySorter.of(sorter));
        Page<String> provinces = stationUseCase.findAllStationProvince(province, pageRequest);

        return MyPageResponse.success(provinces);
    }

    @Override
    public MyPageResponse<String> findAllStationCommune(
            String commune,
            String sorter,
            Integer current,
            Integer pageSize
    ) {
        PageRequest pageRequest = PageRequest.of(current, pageSize, MySorter.of(sorter));
        Page<String> communes = stationUseCase.findAllStationCommune(commune, pageRequest);

        return MyPageResponse.success(communes);
    }

    @Override
    public MyPageResponse<String> findAllStationDistrict(
            String district,
            String sorter,
            Integer current,
            Integer pageSize
    ) {
        PageRequest pageRequest = PageRequest.of(current, pageSize, MySorter.of(sorter));
        Page<String> districts = stationUseCase.findAllStationDistrict(district, pageRequest);

        return MyPageResponse.success(districts);
    }
}
