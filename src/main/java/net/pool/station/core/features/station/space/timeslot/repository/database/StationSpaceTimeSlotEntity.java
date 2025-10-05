package net.pool.station.core.features.station.space.timeslot.repository.database;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import net.pool.station.core.bootstrap.configuration.auditor.Auditor;
import net.pool.station.core.bootstrap.enums.ETimeSlotStatus;
import net.pool.station.core.bootstrap.utils.MyObjectUtils;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "station_space_slots")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StationSpaceTimeSlotEntity extends Auditor {
    @EmbeddedId
    StationSpaceTimeSlotEmbeddedId stationSpaceTimeSlotId;

    String statusCode;

    String statusName;

    @PrePersist
    private void prePersist() {
        if (MyObjectUtils.isEmpty(statusCode)) {
            statusCode = ETimeSlotStatus.ENABLED.getCode();
            statusName = ETimeSlotStatus.ENABLED.getName();
        }
    }
}
