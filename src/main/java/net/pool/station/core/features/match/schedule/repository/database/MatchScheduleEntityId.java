package net.pool.station.core.features.match.schedule.repository.database;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Embeddable
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MatchScheduleEntityId {
    Long matchMakingId;
    Long scheduleId;
}
