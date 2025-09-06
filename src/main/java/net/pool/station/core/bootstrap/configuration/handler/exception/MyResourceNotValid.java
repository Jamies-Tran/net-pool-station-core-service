package net.pool.station.core.bootstrap.configuration.handler.exception;

public class MyResourceNotValid extends RuntimeException {
    public MyResourceNotValid(String message) {
        super(message);
    }

    public MyResourceNotValid() {
        super("Tài nguyên không khả dụng");
    }
}
