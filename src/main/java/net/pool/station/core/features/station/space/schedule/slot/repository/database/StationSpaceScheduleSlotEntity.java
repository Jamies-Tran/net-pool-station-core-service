package net.pool.station.core.features.station.space.schedule.slot.repository.database;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import net.pool.station.core.bootstrap.configuration.auditor.Auditor;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "station_space_schedule_slots")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StationSpaceScheduleSlotEntity extends Auditor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long stationSpaceScheduleSlotId;
    Long timeSlotId;
    Long stationSpaceScheduleId;
}
