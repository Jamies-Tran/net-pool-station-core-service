package net.pool.station.core.domain.space;

import com.fasterxml.jackson.core.type.TypeReference;
import lombok.Builder;
import net.pool.station.core.bootstrap.utils.MyObjectUtils;
import java.util.List;

@Builder
public record SpaceCriteria(
        String search,
        Long stationId,
        List<String> typeCodes,
        List<String> statusCodes
) {
    public SpaceCriteria {
        search = MyObjectUtils.defaultValue(search, new TypeReference<>() {});
        typeCodes = MyObjectUtils.defaultValue(typeCodes, new TypeReference<>() {});
        statusCodes = MyObjectUtils.defaultValue(statusCodes, new TypeReference<>() {});
        stationId = MyObjectUtils.defaultValue(stationId, new TypeReference<>() {});
    }

    public static SpaceCriteria of(
            String search,
            Long stationId,
            List<String> typeCodes,
            List<String> statusCodes
    ) {
        return SpaceCriteria.builder()
                .search(search)
                .stationId(stationId)
                .typeCodes(typeCodes)
                .statusCodes(statusCodes)
                .build();
    }
}
