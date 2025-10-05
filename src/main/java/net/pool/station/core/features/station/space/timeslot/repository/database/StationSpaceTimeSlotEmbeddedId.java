package net.pool.station.core.features.station.space.timeslot.repository.database;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import net.pool.station.core.domain.station.space.timeslot.StationSpaceTimeSlotId;
import net.pool.station.core.features.station.space.space.repository.database.StationSpaceEmbeddedId;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StationSpaceTimeSlotEmbeddedId implements Serializable {
    Long timeSlotId;
    Long stationId;
    Long spaceId;

    @Override
    public int hashCode() {
        return Objects.hash(timeSlotId, stationId, spaceId);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof StationSpaceTimeSlotId)) return false;
        StationSpaceTimeSlotId that = (StationSpaceTimeSlotId) obj;
        return Objects.equals(stationId, that.stationId()) &&
                Objects.equals(spaceId, that.spaceId())
                && Objects.equals(timeSlotId, that.timeSlotId());
    }
}
