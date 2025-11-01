package net.pool.station.core.features.station.station.repository.database;

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
import net.pool.station.core.bootstrap.configuration.common.EnvironmentVariable;
import net.pool.station.core.bootstrap.enums.EStationStatus;
import net.pool.station.core.bootstrap.utils.MyObjectUtils;
import net.pool.station.core.domain.media.Media;
import net.pool.station.core.domain.station.Station;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "stations")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StationEntity extends Auditor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long stationId;

    String stationCode;

    String stationName;

    String avatar;

    String address;

    String province;

    String commune;

    String district;

    String hotline;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "json")
    List<Media> media;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "json")
    Station.Metadata metadata;

    Boolean deleted;

    String statusCode;

    String statusName;

    @PrePersist
    private void prePersist() {
        if (MyObjectUtils.isEmpty(statusCode)) {
            statusCode = EStationStatus.PENDING.getCode();
            statusName = EStationStatus.PENDING.getName();
        }

        if (MyObjectUtils.isEmpty(stationCode)) {
            stationCode = "ST_%s".formatted(UUID.randomUUID().toString());
        }

        metadata = Optional.ofNullable(metadata).orElse(Station.Metadata.empty());

        media = Optional.ofNullable(media).orElse(List.of());

        deleted = false;
    }
}
