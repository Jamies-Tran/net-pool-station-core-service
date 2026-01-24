package net.pool.station.core.features.game.repository.database;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PostPersist;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import net.pool.station.core.bootstrap.enums.EGameStatus;
import net.pool.station.core.bootstrap.utils.MyObjectUtils;
import org.springframework.web.bind.annotation.PostMapping;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "games")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GameEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long gameId;

    Long stationSpaceId;

    String gameCode;

    String gameName;

    String genreCode;

    String genreName;

    String statusCode;

    String statusName;

    Boolean deleted;

    @PrePersist
    private void prePersist() {
        deleted = false;
    }

    @PostPersist
    private void postPersist() {
        if (MyObjectUtils.isEmpty(statusCode)) {
            statusCode = EGameStatus.ENABLE.getCode();
            statusName = EGameStatus.ENABLE.getName();
        }
        gameCode = gameCode.concat("_%s".formatted(gameId));
    }
}
