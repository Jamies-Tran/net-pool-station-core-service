package net.pool.station.core.domain.logging.factory;

public interface LoggingFactory<T> {
    void log(T data);
}
