package net.pool.station.core.features.station.resource.repository.database;

import com.fasterxml.jackson.core.type.TypeReference;
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
import net.pool.station.core.bootstrap.enums.EResourceStatus;
import net.pool.station.core.bootstrap.utils.MyObjectUtils;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.Type;
import org.hibernate.type.SqlTypes;

import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "station_resources")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StationResourceEntity extends Auditor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long stationResourceId;

    Long areaId;

    String resourceCode;

    String resourceName;

    String typeCode;

    String typeName;

    String statusCode;

    String statusName;

    Boolean deleted;

//    @JdbcTypeCode(SqlTypes.JSON)
//    @Column(columnDefinition = "json")
//    Map<String, Object> specs;

    @PrePersist
    private void prePersist() {
        if (MyObjectUtils.isEmpty(statusCode)) {
            statusCode = EResourceStatus.ENABLE.getCode();
            statusName = EResourceStatus.ENABLE.getName();
        }
        deleted = false;
//        specs = MyObjectUtils.defaultValue(specs, new TypeReference<>() {});
    }
}
