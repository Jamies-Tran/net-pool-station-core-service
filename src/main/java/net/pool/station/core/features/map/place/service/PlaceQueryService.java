package net.pool.station.core.features.map.place.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import net.pool.station.core.domain.map.place.Place;
import net.pool.station.core.domain.map.place.detail.PlaceDetail;
import net.pool.station.core.features.map.place.repository.feign.models.PlaceDetailFeign;
import net.pool.station.core.features.map.place.repository.feign.models.PlaceDetailFeignMapper;
import net.pool.station.core.features.map.place.repository.feign.models.PlaceFeignMapper;
import net.pool.station.core.features.map.place.repository.feign.PlacePlaceHolder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PlaceQueryService {
    PlacePlaceHolder placeHolder;

    PlaceFeignMapper placeFeignMapper;

    PlaceDetailFeignMapper placeDetailFeignMapper;

    protected List<Place.Prediction> findPlaceByAddress(
            String address,
            Integer limit,
            Boolean moreCompound
    ) {
        return placeFeignMapper
                .toDto(placeHolder
                        .autocomplete(address, limit, moreCompound).predictions());
    }

    protected Optional<PlaceDetail.Result> findPlaceDetailByPlaceId(String placeId) {
        try {
            return Optional.ofNullable(placeDetailFeignMapper.toDto(placeHolder.findDetail(placeId).result()));
        } catch (Exception e) {
            log.error("[PlaceQueryService.findPlaceDetailByPlaceId(...)] message: {}", e.getMessage(), e);
            return Optional.empty();
        }
    }
}
