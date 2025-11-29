package net.pool.station.core.features.space.repository.database;

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
import net.pool.station.core.bootstrap.enums.ESpaceStatus;
import net.pool.station.core.bootstrap.utils.MyObjectUtils;
import org.hibernate.annotations.JdbcType;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.sql.JDBCType;
import java.sql.SQLType;
import java.util.Map;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "spaces")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SpaceEntity extends Auditor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long spaceId;

    String typeCode;

    String typeName;

    String statusCode;

    String statusName;

    @JdbcTypeCode(value = SqlTypes.JSON)
    @Column(columnDefinition = "json")
    Map<String, Object> metadata;

    Boolean deleted;

    @PrePersist
    private void prePersist() {
        if (MyObjectUtils.isEmpty(statusCode)) {
            statusCode = ESpaceStatus.ACTIVE.getCode();
            statusName = ESpaceStatus.ACTIVE.getName();
        }

        if (MyObjectUtils.isEmpty(typeCode)) {
            typeCode = UUID.randomUUID().toString();
        }

        deleted = false;
    }
}
