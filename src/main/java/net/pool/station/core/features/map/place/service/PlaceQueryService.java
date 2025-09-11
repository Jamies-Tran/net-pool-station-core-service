package net.pool.station.core.features.map.place.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.pool.station.core.domain.map.place.Place;
import net.pool.station.core.features.map.place.repository.feign.PlaceFeignMapper;
import net.pool.station.core.features.map.place.repository.feign.PlacePlaceHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PlaceQueryService {
    PlacePlaceHolder placeHolder;

    PlaceFeignMapper feignMapper;

    protected List<Place.Prediction> findPlaceByAddress(
            String address,
            Integer limit,
            Boolean moreCompound
    ) {
        return feignMapper
                .toDto(placeHolder
                        .autocomplete(address, limit, moreCompound).predictions());
    }
}
