package net.pool.station.core.bootstrap.configuration.handler.exception;

public class MyLoginInvalidException extends RuntimeException {
    public MyLoginInvalidException(String message) {
        super(message);
    }

    public MyLoginInvalidException() {
        super("Đăng nhập không hợp lệ");
    }
}
