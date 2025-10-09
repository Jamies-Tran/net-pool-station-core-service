package net.pool.station.core.domain.station.menu;

import com.fasterxml.jackson.core.type.TypeReference;
import lombok.Builder;
import net.pool.station.core.bootstrap.utils.MyObjectUtils;
import net.pool.station.core.features.station.menu.repository.database.StationMenuEntity;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

@Builder
public record StationMenuCriteria(
        String search,
        Long stationId,
        List<Long> priceRange,
        List<String> typeCodes,
        List<String> statusCodes
) {
    public StationMenuCriteria {
        search = MyObjectUtils.defaultValue(search, new TypeReference<>() {});
        stationId = MyObjectUtils.defaultValue(stationId, new TypeReference<>() {});
        statusCodes = MyObjectUtils.defaultValue(statusCodes, new TypeReference<>() {});
        typeCodes = MyObjectUtils.defaultValue(typeCodes, new TypeReference<>() {});
        priceRange = MyObjectUtils.defaultValue(priceRange, new TypeReference<>() {});
    }

    public static StationMenuCriteria of(
            String search,
            Long stationId,
            List<Long> priceRange,
            List<String> statusCodes,
            List<String> typeCodes
    ) {
        return StationMenuCriteria.builder()
                .search(search)
                .stationId(stationId)
                .priceRange(priceRange)
                .statusCodes(statusCodes)
                .typeCodes(typeCodes)
                .build();
    }

    public Specification<StationMenuEntity> specification() {
        List<Specification<StationMenuEntity>> specifications = new ArrayList<>();

        specifications.add((root, query, cb) -> cb.equal(root.get("deleted"), false));

        if (MyObjectUtils.isNotEmpty(search)) {
            specifications.add((root, query, cb) -> cb.or(
                    cb.like(root.get("menuName"), "%:search%".replace(":search", search)),
                    cb.equal(root.get("menuCode"), search)
            ));
        }

        if (MyObjectUtils.isNotEmpty(stationId)) {
            specifications.add((root, query, cb) -> cb.equal(root.get("stationId"), stationId));
        }

        if (MyObjectUtils.isNotEmpty(statusCodes)) {
            specifications.add((root, query, cb) -> root.get("statusCode").in(statusCodes));
        }

        if (MyObjectUtils.isNotEmpty(typeCodes)) {
            specifications.add((root, query, cb) -> root.get("typeCode").in(typeCodes));
        }

        if (MyObjectUtils.isNotEmpty(priceRange) && MyObjectUtils.isEquals(priceRange.size(), 2)) {
            specifications.add((root, query, cb) -> cb
                    .between(root.get("price"), priceRange.get(0), priceRange.get(1)));
        }

        if (MyObjectUtils.isNotEmpty(specifications)) {
            return specifications.stream()
                    .reduce(Specification.where(null), Specification::and);
        } else {
            return Specification.where(null);
        }

    }
}
