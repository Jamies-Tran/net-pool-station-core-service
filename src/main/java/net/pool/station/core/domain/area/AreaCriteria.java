package net.pool.station.core.domain.area;

import com.fasterxml.jackson.core.type.TypeReference;
import lombok.Builder;
import net.pool.station.core.bootstrap.utils.MyObjectUtils;

import java.util.List;

@Builder
public record AreaCriteria(
        String search,
        Long stationSpaceId,
        List<String> statusCodes,
        List<String> typeCodes
) {
    public AreaCriteria {
        search = MyObjectUtils.defaultValue(search, new TypeReference<>() {});
        stationSpaceId = MyObjectUtils.defaultValue(stationSpaceId, new TypeReference<>() {});
        statusCodes = MyObjectUtils.defaultValue(statusCodes, new TypeReference<>() {});
        typeCodes = MyObjectUtils.defaultValue(typeCodes, new TypeReference<>() {});
    }

    public static AreaCriteria of(
            String search,
            Long stationSpaceId,
            List<String> statusCodes,
            List<String> typeCodes
    ) {
        return AreaCriteria.builder()
                .search(search)
                .stationSpaceId(stationSpaceId)
                .statusCodes(statusCodes)
                .typeCodes(typeCodes)
                .build();
    }
}
