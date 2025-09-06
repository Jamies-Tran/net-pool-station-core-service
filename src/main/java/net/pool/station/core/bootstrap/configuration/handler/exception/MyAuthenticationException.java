package net.pool.station.core.bootstrap.configuration.handler.exception;

public class MyAuthenticationException extends RuntimeException {

    public MyAuthenticationException(String message) {
        super(message);
    }

    public MyAuthenticationException() {
        super("Xác thực tài khoản thất bại.");
    }
}
