package net.pool.station.core.bootstrap.configuration.handler.exception;

public class MyResourceNotFoundException extends RuntimeException {
    public MyResourceNotFoundException(String message) {
        super(message);
    }

    public MyResourceNotFoundException() {
        super("Không tìm thấy tài nguyên");
    }
}
