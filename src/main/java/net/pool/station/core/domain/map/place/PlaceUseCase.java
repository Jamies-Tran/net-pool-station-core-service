package net.pool.station.core.domain.map.place;

import java.util.List;

public interface PlaceUseCase {
    List<Place.Prediction> autocomplete(String address, Integer limit, Boolean moreCompound);
}
