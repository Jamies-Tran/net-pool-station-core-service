package net.pool.station.core.features.station.space.space.repository.database;

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
import net.pool.station.core.bootstrap.enums.EStationSpaceStatus;
import net.pool.station.core.bootstrap.utils.MyObjectUtils;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "station_spaces")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StationSpaceEntity extends Auditor {
    @EmbeddedId
    StationSpaceEmbeddedId stationSpaceId;

    String spaceCode;

    String spaceName;

    Integer capacity;

    String statusCode;

    String statusName;

    Boolean deleted;

    @PrePersist
    private void prePersist() {
        if (MyObjectUtils.isEmpty(statusCode)) {
            statusCode = EStationSpaceStatus.ACTIVE.getCode();
            statusName = EStationSpaceStatus.ACTIVE.getName();
        }

        deleted = false;
    }
}
