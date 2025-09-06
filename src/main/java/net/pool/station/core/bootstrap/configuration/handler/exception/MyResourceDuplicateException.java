package net.pool.station.core.bootstrap.configuration.handler.exception;

public class MyResourceDuplicateException extends RuntimeException {
    public MyResourceDuplicateException(String message) {
        super(message);
    }

    public MyResourceDuplicateException() {
        super("Tài nguyên bị trùng lập");
    }
}
