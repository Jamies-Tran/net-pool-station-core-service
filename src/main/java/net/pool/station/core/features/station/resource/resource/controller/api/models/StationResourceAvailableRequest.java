package net.pool.station.core.features.station.resource.resource.controller.api.models;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public record StationResourceAvailableRequest(
        @NotNull
        LocalDate date,
        @NotNull
        LocalTime begin,
        @NotNull
        LocalTime end
) {
}
