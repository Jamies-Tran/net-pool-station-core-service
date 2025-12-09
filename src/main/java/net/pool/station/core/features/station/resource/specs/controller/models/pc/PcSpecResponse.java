package net.pool.station.core.features.station.resource.specs.controller.models.pc;

import lombok.Builder;

@Builder
public record PcSpecResponse(
        String pcCpu,

        String pcRam,

        String pcGpu,

        String pcMonitor,

        String pcKeyboard,

        String pcMouse,

        String pcHeadphone
) {
}
