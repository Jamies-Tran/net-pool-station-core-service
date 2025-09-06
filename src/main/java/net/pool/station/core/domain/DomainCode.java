package net.pool.station.core.domain;

public record DomainCode<T>(T value) {
    public static <T> DomainCode<T> of(T value) {
        return new DomainCode<>(value);
    }
}
