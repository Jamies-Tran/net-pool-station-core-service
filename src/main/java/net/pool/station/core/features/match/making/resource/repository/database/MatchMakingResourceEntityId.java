package net.pool.station.core.features.match.making.resource.repository.database;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Embeddable
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MatchMakingResourceEntityId {
    Long matchMakingId;

    Long stationResourceId;
}
