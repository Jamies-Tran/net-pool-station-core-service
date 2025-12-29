package net.pool.station.core.features.match.making.slot.respository.database;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;

@Embeddable
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MatchMakingSlotEntityId implements Serializable {
    Long matchMakingId;
    Long timeSlotId;
}
