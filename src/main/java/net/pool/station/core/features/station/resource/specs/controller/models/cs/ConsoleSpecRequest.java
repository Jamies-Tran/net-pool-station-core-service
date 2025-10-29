package net.pool.station.core.features.station.resource.specs.controller.models.cs;

public record ConsoleSpecRequest(
        Long areaId,

        Double csScreenSize,

        String csResolution,

        String csRefreshRate
) {
}
