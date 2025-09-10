package net.pool.station.core.bootstrap.utils;

import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.Objects;

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

    public static Long valueOf(String value) {
        try {
            return Long.valueOf(value);
        } catch (Exception e) {
            return 0L;
        }
    }
}
