package net.pool.station.core.features.station.resource.specs.repository.database;

import jakarta.persistence.Column;
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
import net.pool.station.core.bootstrap.utils.MyObjectUtils;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "station_resource_specs")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StationResourceSpecEntity extends Auditor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long stationResourceSpecId;

    Long stationResourceId;

    String pcCpu;

    String pcRam;

    String pcGpu;

    String pcMonitor;

    String pcKeyboard;

    String pcMouse;

    String pcHeadphone;

    String btTableDetail;

    String btCueDetail;

    String btBallDetail;

    String csConsoleModel;

    String csTvModel;

    String csControllerType;

    Integer csControllerCount;

    String typeCode;

    String typeName;

    Boolean deleted;

    @PrePersist
    private void prePersist() {
        deleted = false;
    }
}
