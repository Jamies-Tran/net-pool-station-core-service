package net.pool.station.core.features.timeslot.repository.database;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.pool.station.core.bootstrap.configuration.auditor.Auditor;
import net.pool.station.core.bootstrap.enums.ETimeSlotStatus;
import net.pool.station.core.bootstrap.utils.MyObjectUtils;

import java.time.LocalTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "time_slots")
public class TimeSlotEntity extends Auditor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long timeSlotId;

    Long scheduleId;

    LocalTime begin;

    LocalTime end;

    String periodCode;

    String periodName;

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
