package net.pool.station.core.features.station.resource.specs.controller.models.cs;

import lombok.Builder;

@Builder
public record ConsoleSpecResponse(
        String csConsoleModel,

        String csTvModel,

        String csControllerType,

        Integer csControllerCount
) {
}
