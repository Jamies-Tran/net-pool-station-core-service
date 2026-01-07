package net.pool.station.core.features.notification.repository.database;

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
import net.pool.station.core.bootstrap.enums.ENotificationStatus;
import net.pool.station.core.bootstrap.utils.MyObjectUtils;
import org.flywaydb.core.internal.jdbc.JdbcNullTypes;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "notifications")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NotificationEntity extends Auditor {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long notificationId;
    Long accountId;
    String title;
    String description;
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "json")
    Map<String, String> payload;
    String typeCode;
    String typeName;
    String statusCode;
    String statusName;

    @PrePersist
    private void prePersist() {
        if (MyObjectUtils.isEmpty(statusCode)) {
            statusCode = ENotificationStatus.NEW.getCode();
            statusName = ENotificationStatus.NEW.getName();
        }
    }
}
