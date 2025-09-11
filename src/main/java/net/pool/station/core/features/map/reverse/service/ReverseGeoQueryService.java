package net.pool.station.core.features.map.reverse.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.pool.station.core.domain.map.reverse.ReverseGeo;
import net.pool.station.core.features.map.reverse.repository.feign.ReverseGeoFeignMapper;
import net.pool.station.core.features.map.reverse.repository.feign.ReverseGeoPlaceHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ReverseGeoQueryService {
    ReverseGeoPlaceHolder placeHolder;

    ReverseGeoFeignMapper feignMapper;

    protected Optional<ReverseGeo.Result> findByLatLng(Double latitude, Double longitude) {
        try {
            return placeHolder.findReverseGeo("%s,%s".formatted(latitude.toString(), longitude.toString()))
                    .results()
                    .stream()
                    .findAny()
                    .map(feignMapper::toDto);
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}
