package net.pool.station.core.features.match.making.resource.repository.database;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "match_making_resources")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MatchMakingResourceEntity {
    @EmbeddedId
    MatchMakingResourceEntityId id;
}
