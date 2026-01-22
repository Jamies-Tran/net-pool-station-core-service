package net.pool.station.core.features.match.making.match.making.repository.database;

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
import net.pool.station.core.bootstrap.enums.EMatchMakingStatus;
import net.pool.station.core.bootstrap.utils.MyObjectUtils;
import net.pool.station.core.domain.match.making.MatchMaking;
import org.hibernate.annotations.JdbcType;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "match_making")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MatchMakingEntity extends Auditor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long matchMakingId;
    Long stationId;
    Long gameId;
    Long scheduleId;
    String matchMakingCode;
    Integer numberOfHoldingDay;
    Integer limitParticipant;
    Integer totalPrice;
    LocalDate expiredAt;
    LocalDate processAt;
    LocalDateTime playAt;
    String resourceTypeCode;
    String resourceTypeName;
    String typeCode;
    String typeName;
    String paymentMethodCode;
    String paymentMethodName;
    LocalDateTime paidDepositAt;
    String statusCode;
    String statusName;
    Boolean deleted;

    @PrePersist
    private void prePersist() {
        if (MyObjectUtils.isEmpty(statusCode)) {
            statusCode = EMatchMakingStatus.DRAFT.getCode();
            statusName = EMatchMakingStatus.DRAFT.getName();
        }

        deleted = false;
    }
}
