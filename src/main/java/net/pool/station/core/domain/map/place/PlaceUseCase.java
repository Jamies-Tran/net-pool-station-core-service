package net.pool.station.core.domain.map.place;

import net.pool.station.core.domain.map.place.detail.PlaceDetail;
import net.pool.station.core.features.map.place.repository.feign.models.PlaceDetailFeign;

import java.util.List;
import java.util.Optional;

public interface PlaceUseCase {
    List<Place.Prediction> autocomplete(String address, Integer limit, Boolean moreCompound);

    Optional<PlaceDetail.Result> findDetailByPlaceId(String placeId);
}
