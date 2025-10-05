package net.pool.station.core.features.station.account.repository.database;

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
import net.pool.station.core.bootstrap.enums.EAccountStatus;
import net.pool.station.core.bootstrap.utils.MyObjectUtils;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "station_account")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StationAccountEntity extends Auditor {
    @EmbeddedId
    StationAccountEmbeddedId stationAccountId;

    String statusCode;

    String statusName;

    @PrePersist
    private void prePersist() {
        if (MyObjectUtils.isEmpty(statusCode)) {
            statusCode = EAccountStatus.ENABLE.getCode();
            statusName = EAccountStatus.ENABLE.getName();
        }
    }
}
