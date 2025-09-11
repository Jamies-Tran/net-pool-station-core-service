package net.pool.station.core.features.map.reverse.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.pool.station.core.domain.map.reverse.ReverseGeo;
import net.pool.station.core.domain.map.reverse.ReverseGeoUseCase;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ReverseGeoUseCaseService implements ReverseGeoUseCase {
    ReverseGeoQueryService queryService;

    @Override
    @Transactional(readOnly = true)
    public Optional<ReverseGeo.Result> reverseGeo(Double latitude, Double longitude) {
        return queryService.findByLatLng(latitude, longitude);
    }
}
