package net.pool.station.core.domain.station.space;

import com.fasterxml.jackson.core.type.TypeReference;
import lombok.Builder;
import net.pool.station.core.bootstrap.utils.MyObjectUtils;

import java.util.List;

@Builder
public record StationSpaceCriteria(
        String search,
        Long stationId,
        List<String> typeCodes,
        List<String> statusCodes
) {
    public StationSpaceCriteria {
        search = MyObjectUtils.defaultValue(search, new TypeReference<String>() {});
        stationId = MyObjectUtils.defaultValue(stationId, new TypeReference<Long>() {});
        typeCodes = MyObjectUtils.defaultValue(typeCodes, new TypeReference<List<String>>() {});
        statusCodes = MyObjectUtils.defaultValue(statusCodes, new TypeReference<List<String>>() {});
    }

    public static StationSpaceCriteria of(
            String search,
            Long stationId,
            List<String> typeCodes,
            List<String> statusCodes
    ) {
        return StationSpaceCriteria.builder()
                .search(search)
                .stationId(stationId)
                .typeCodes(typeCodes)
                .statusCodes(statusCodes)
                .build();
    }
}
