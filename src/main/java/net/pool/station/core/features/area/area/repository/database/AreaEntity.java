package net.pool.station.core.features.area.area.repository.database;

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
@Table(name = "areas")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AreaEntity extends Auditor {
    @Id
    Long areaId;

    Long stationId;

    Long spaceId;

    Long areaTypeId;

    String areaCode;

    String areaName;

    String statusCode;

    String statusName;

    Boolean deleted;
}
