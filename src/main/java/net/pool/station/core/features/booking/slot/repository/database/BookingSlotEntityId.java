package net.pool.station.core.features.booking.slot.repository.database;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Embeddable
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BookingSlotEntityId {
    Long bookingId;
    Long timeSlotId;
}
