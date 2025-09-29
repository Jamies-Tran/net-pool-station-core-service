package net.pool.station.core.domain;

public record DomainKey<T>(T value) {
    public static <T> DomainKey<T> of(T value) {
        return new DomainKey<>(value);
    }
}
