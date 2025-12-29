package net.pool.station.core.features.booking.booking.repository.database;

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
import net.pool.station.core.bootstrap.enums.EBookingStatus;
import net.pool.station.core.bootstrap.enums.EPaymentMethod;
import net.pool.station.core.bootstrap.enums.EPaymentType;
import net.pool.station.core.bootstrap.utils.MyObjectUtils;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "bookings")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BookingEntity extends Auditor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long bookingId;
    Long accountId;
    Long scheduleId;
    Long matchMakingId;
    Long stationResourceId;
    String bookingCode;
    String typeCode;
    String typeName;
    String cancelReason;
    LocalDateTime startAt;
    LocalDateTime endAt;
    String paymentMethodCode;
    String paymentMethodName;
    String statusCode;
    String statusName;
    Boolean deleted;

    @PrePersist
    private void prePersist() {
        if (MyObjectUtils.isEmpty(statusCode)) {
            if (MyObjectUtils.isEquals(EPaymentMethod.BANK_TRANSFER,
                    EPaymentMethod.valueOf(paymentMethodCode))) {
                statusCode = EBookingStatus.PENDING.getCode();
                statusName = EBookingStatus.PENDING.getName();
            } else {
                statusCode = EBookingStatus.NEW.getCode();
                statusName = EBookingStatus.NEW.getName();
            }

        }
        if (!StringUtils.hasText(bookingCode)) {
            bookingCode = "BOOKING_%s".formatted(System.currentTimeMillis());
        }
        deleted = false;
    }
}
