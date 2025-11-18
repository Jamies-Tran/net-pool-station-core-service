package net.pool.station.core.features.booking.resource.repository.database;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import net.pool.station.core.bootstrap.configuration.auditor.Auditor;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "booking_resource")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BookingResourceEntity extends Auditor {
    @EmbeddedId
    BookingResourceEntityId id;
}
