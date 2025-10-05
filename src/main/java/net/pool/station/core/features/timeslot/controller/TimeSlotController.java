package net.pool.station.core.features.timeslot.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.pool.station.core.bootstrap.configuration.handler.exception.MyResourceNotFoundException;
import net.pool.station.core.bootstrap.rest.response.MyValueResponse;
import net.pool.station.core.domain.DomainKey;
import net.pool.station.core.domain.timeslot.TimeSlotUseCase;
import net.pool.station.core.features.timeslot.controller.models.TimeSlotResponse;
import net.pool.station.core.features.timeslot.controller.models.TimeSlotResponseMapper;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TimeSlotController implements TimeSlotApi {
    TimeSlotUseCase timeSlotUseCase;

    @Override
    public MyValueResponse<?> delete(Long timeSlotId) {
        timeSlotUseCase.delete(DomainKey.of(timeSlotId));

        return MyValueResponse.successNoData();
    }
}
