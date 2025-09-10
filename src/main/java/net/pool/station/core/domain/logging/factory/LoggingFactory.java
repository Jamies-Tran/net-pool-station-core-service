package net.pool.station.core.domain.logging.factory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface LoggingFactory<T> {
    void log(T data);

    <C> Page<T> findAll(C criteria, Pageable pageable);
}
