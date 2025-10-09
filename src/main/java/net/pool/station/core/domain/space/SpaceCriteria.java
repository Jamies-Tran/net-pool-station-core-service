package net.pool.station.core.domain.space;

import com.fasterxml.jackson.core.type.TypeReference;
import lombok.Builder;
import net.pool.station.core.bootstrap.utils.MyObjectUtils;
import java.util.List;

@Builder
public record SpaceCriteria(
        String search,
        List<String> statusCodes
) {
    public SpaceCriteria {
        search = MyObjectUtils.defaultValue(search, new TypeReference<>() {});
        statusCodes = MyObjectUtils.defaultValue(statusCodes, new TypeReference<>() {});
    }

    public static SpaceCriteria of(
            String search,
            List<String> statusCodes
    ) {
        return SpaceCriteria.builder()
                .search(search)
                .statusCodes(statusCodes)
                .build();
    }
}
