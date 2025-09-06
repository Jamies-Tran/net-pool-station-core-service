package net.pool.station.core.bootstrap.configuration.handler.exception;

public class MyAccessTokenExpireException extends RuntimeException{
    public MyAccessTokenExpireException(String message) {
        super(message);
    }

    public MyAccessTokenExpireException() {
        super("Phiên đăng nhập đã hết hạn.");
    }
}
