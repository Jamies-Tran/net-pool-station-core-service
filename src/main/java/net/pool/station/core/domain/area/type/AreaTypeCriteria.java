package net.pool.station.core.domain.area.type;

import com.fasterxml.jackson.core.type.TypeReference;
import lombok.Builder;
import net.pool.station.core.bootstrap.utils.MyObjectUtils;

import java.util.List;

@Builder
public record AreaTypeCriteria(
        String search,
        List<String> typeCodes,
        List<String> statusCodes
) {
    public AreaTypeCriteria {
        search = MyObjectUtils.defaultValue(search, new TypeReference<>() {});
        typeCodes = MyObjectUtils.defaultValue(typeCodes, new TypeReference<>() {});
        statusCodes = MyObjectUtils.defaultValue(statusCodes, new TypeReference<>() {});
    }

    public static AreaTypeCriteria of(
            String search,
            List<String> typeCodes,
            List<String> statusCodes
    ) {
        return AreaTypeCriteria.builder()
                .search(search)
                .typeCodes(typeCodes)
                .statusCodes(statusCodes)
                .build();
    }
}
