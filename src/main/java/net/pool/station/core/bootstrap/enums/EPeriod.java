package net.pool.station.core.bootstrap.enums;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.pool.station.core.bootstrap.configuration.enums.EnumProperty;

import java.time.LocalTime;

@Getter
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum EPeriod implements EnumProperty {
    MORNING("MORNING", "Sáng"),
    AFTERNOON("AFTERNOON", "Chiều"),
    EVENING("EVENING", "Tối"),
    NIGHT("NIGHT", "Đêm"),
    UNKNOWN("UNKNOWN", "Không xác định");

    String code;
    String name;

    public static EPeriod getPeriod(LocalTime time) {
         if (time.isBefore(LocalTime.of(6, 0))){
            return NIGHT;
        } else if (time.isBefore(LocalTime.of(12, 0))) {
            return MORNING;
        } else if (time.isBefore(LocalTime.of(18, 0))) {
            return AFTERNOON;
        } else if (time.isBefore(LocalTime.of(23, 59))) {
            return EVENING;
        }

        return UNKNOWN;
    }
}
