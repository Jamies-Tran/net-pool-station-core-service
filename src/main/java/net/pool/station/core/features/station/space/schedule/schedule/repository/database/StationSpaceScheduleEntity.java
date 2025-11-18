package net.pool.station.core.features.station.space.schedule.schedule.repository.database;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import net.pool.station.core.bootstrap.configuration.auditor.Auditor;
import net.pool.station.core.bootstrap.enums.EStationSpaceScheduleStatus;
import net.pool.station.core.bootstrap.utils.MyObjectUtils;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "station_space_schedules")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StationSpaceScheduleEntity extends Auditor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long stationSpaceScheduleId;
    Long scheduleId;
    Long stationSpaceId;
    Boolean deleted;
    String statusCode;
    String statusName;

    @PrePersist
    private void prePersist() {
        if (MyObjectUtils.isEmpty(statusCode)) {
            statusCode = EStationSpaceScheduleStatus.ENABLE.getCode();
            statusName = EStationSpaceScheduleStatus.ENABLE.getName();
        }
        deleted = false;
    }
}
