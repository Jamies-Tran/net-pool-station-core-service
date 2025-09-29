package net.pool.station.core.domain.area.type;

public record AreaType(
        Long areaTypeId,
        String typeCode,
        String typeName,
        String statusCode,
        String statusName,
        Boolean deleted
) {
}
