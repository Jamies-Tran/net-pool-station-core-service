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

    String pcGpuModel;

    String pcGpuSerial;

    String pcGpuCapacity;

    String pcStorageName;

    @Column(name = "pc_storage_vram")
    String pcStorageVRam;

    String btTypeCode;

    String btTypeName;

    String btSurfaceTypeCode;

    String btSurfaceTypeName;

    String btClothTypeCode;

    String btClothTypeName;

    Double csScreenSize;

    String csResolution;

    String csRefreshRate;

    String typeCode;

    String typeName;

    Boolean deleted;

    @PrePersist
    private void prePersist() {
        deleted = false;
    }
}
