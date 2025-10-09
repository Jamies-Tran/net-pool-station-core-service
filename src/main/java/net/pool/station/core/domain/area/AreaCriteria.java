package net.pool.station.core.domain.area;

import com.fasterxml.jackson.core.type.TypeReference;
import lombok.Builder;
import net.pool.station.core.bootstrap.utils.MyObjectUtils;

import java.util.List;

@Builder
public record AreaCriteria(
        String search,
        Long stationId,
        Long spaceId,
        List<String> statusCodes,
        List<String> typeCodes
) {
    public AreaCriteria {
        search = MyObjectUtils.defaultValue(search, new TypeReference<>() {});
        stationId = MyObjectUtils.defaultValue(stationId, new TypeReference<>() {});
        spaceId = MyObjectUtils.defaultValue(spaceId, new TypeReference<>() {});
        statusCodes = MyObjectUtils.defaultValue(statusCodes, new TypeReference<>() {});
        typeCodes = MyObjectUtils.defaultValue(typeCodes, new TypeReference<>() {});
    }

    public static AreaCriteria of(
            String search,
            Long stationId,
            Long spaceId,
            List<String> statusCodes,
            List<String> typeCodes
    ) {
        return AreaCriteria.builder()
                .search(search)
                .stationId(stationId)
                .spaceId(spaceId)
                .statusCodes(statusCodes)
                .typeCodes(typeCodes)
                .build();
    }
}
