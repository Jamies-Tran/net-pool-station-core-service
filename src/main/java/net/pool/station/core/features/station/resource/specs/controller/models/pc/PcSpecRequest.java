package net.pool.station.core.features.station.resource.specs.controller.models.pc;

public record PcSpecRequest(
        String pcCpu,

        String pcRam,

        String pcGpu,

        String pcMonitor,

        String pcKeyboard,

        String pcMouse,

        String pcHeadphone
) {


}
