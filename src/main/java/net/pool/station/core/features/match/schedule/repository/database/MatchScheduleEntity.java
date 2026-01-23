package net.pool.station.core.features.match.schedule.repository.database;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "match_schedule")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MatchScheduleEntity {
    @EmbeddedId
    MatchScheduleEntityId id;
}
