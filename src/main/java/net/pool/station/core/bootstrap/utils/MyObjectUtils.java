package net.pool.station.core.bootstrap.utils;

import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@SuppressWarnings("unchecked")
public class MyObjectUtils {
    public static <T> Boolean isEquals(T t1 , T t2) {
        return Objects.equals(t1, t2);
    }

    public static <T> Boolean isNotEquals(T t1 , T t2) {
        return !Objects.equals(t1, t2);
    }

    public static <T> Boolean isEqualIgnoreCase(T t1 , T t2) {
        if (t1 instanceof String && t2 instanceof String) {
            return ((String) t1).equalsIgnoreCase((String) t2);
        }
        return Objects.equals(t1, t2);
    }

    public static <T> Boolean isNotEmpty(T t) {
        if (t instanceof String) {
            return StringUtils.hasText((String) t);
        }

        if (t instanceof Collection) {
            return !((Collection<?>) t).isEmpty();
        }

        return t != null;
    }

    public static <T> Boolean isEmpty(T t) {
        if (t instanceof String) {
            return !StringUtils.hasText((String) t);
        }

        if (t instanceof Collection) {
            return ((Collection<?>) t).isEmpty();
        }

        return t == null;
    }

    public static <T> Long convertToLong(T value) {
        try {
            if (value instanceof String stringValue) {
                return Long.valueOf(stringValue);
            }

            return (Long) value;
        } catch (Exception e) {
            return 0L;
        }
    }

    public static <T> String convertToString(T value) {
        try {
            return String.valueOf(value);
        } catch (Exception e) {
            return "";
        }
    }

    public static <T> T defaultValue(T value) {
        if (value instanceof String stringValue) {
            return (T) Optional.of(stringValue).orElse("");
        }

        if (value instanceof Collection<?> collectionValue) {
            return (T) Optional.of(collectionValue).orElse(List.of());
        }

        return value;
    }

    public static <T> Boolean compareIgnoreCase(T value1, T value2) {
        if (value1 instanceof String stringValue1 && value2 instanceof String stringValue2) {
            return stringValue1.equalsIgnoreCase(stringValue2);
        }

        return Objects.equals(value1, value2);
    }

    public static <T> Boolean containIgnoreCase(T value1, T value2) {
        if (value1 instanceof String stringValue1 && value2 instanceof String stringValue2) {
            return stringValue1.toLowerCase()
                    .contains(stringValue2.toLowerCase());
        }

        return false;
    }
}
