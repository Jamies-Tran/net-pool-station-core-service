package net.pool.station.core.features.map.place.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.pool.station.core.domain.map.place.Place;
import net.pool.station.core.domain.map.place.PlaceUseCase;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PlaceUseCaseService implements PlaceUseCase {
    PlaceQueryService queryService;

    @Override
    @Transactional(readOnly = true)
    public List<Place.Prediction> autocomplete(String address, Integer limit, Boolean moreCompound) {
        return queryService.findPlaceByAddress(address, limit, moreCompound);
    }
}
