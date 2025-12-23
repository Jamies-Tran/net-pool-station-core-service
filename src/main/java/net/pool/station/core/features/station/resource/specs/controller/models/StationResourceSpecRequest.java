package net.pool.station.core.features.station.resource.specs.controller.models;

import lombok.With;
import net.pool.station.core.features.station.resource.specs.controller.models.bt.BilliardTableSpecRequest;
import net.pool.station.core.features.station.resource.specs.controller.models.cs.ConsoleSpecRequest;
import net.pool.station.core.features.station.resource.specs.controller.models.pc.PcSpecRequest;

public record StationResourceSpecRequest(
        Long stationResourceId,
        PcSpecRequest pc,
        BilliardTableSpecRequest billiardTable,
        ConsoleSpecRequest console,
        @With String typeCode,
        @With String typeName
) {
}
