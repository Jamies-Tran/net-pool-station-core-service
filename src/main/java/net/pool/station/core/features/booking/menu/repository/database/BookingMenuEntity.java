package net.pool.station.core.features.booking.menu.repository.database;

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
@Table(name = "booking_menus")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BookingMenuEntity extends Auditor {
    @EmbeddedId
    BookingMenuEntityId bookingMenuId;
}
