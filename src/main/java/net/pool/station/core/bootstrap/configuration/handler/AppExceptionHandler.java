package net.pool.station.core.bootstrap.configuration.handler;

import io.jsonwebtoken.ExpiredJwtException;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import net.pool.station.core.bootstrap.configuration.handler.exception.EErrorCode;
import net.pool.station.core.bootstrap.configuration.handler.exception.MyAuthenticationException;
import net.pool.station.core.bootstrap.configuration.handler.exception.MyResourceDuplicateException;
import net.pool.station.core.bootstrap.configuration.handler.exception.MyResourceNotFoundException;
import net.pool.station.core.bootstrap.configuration.handler.exception.MyResourceNotValid;
import net.pool.station.core.bootstrap.rest.response.MyValueResponse;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AppExceptionHandler {
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(MyAuthenticationException.class)
    public MyValueResponse<?> authenticationExceptionHandler(MyAuthenticationException exc) {
        return MyValueResponse.error(
                exc.getMessage(),
                HttpStatus.UNAUTHORIZED.getReasonPhrase(),
                EErrorCode.AUTHORIZE_EXCEPTION.getCode()
        );
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public MyValueResponse<?> methodArgumentExceptionHandler(MethodArgumentNotValidException exc) {
        Map<String, String> errors = new LinkedHashMap<>();
        for (FieldError fieldError : exc.getBindingResult().getFieldErrors()) {
            errors.put("message", fieldError.getDefaultMessage());
        }
        return MyValueResponse.error(
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                EErrorCode.RESOURCE_VALIDATE_FAIL.getCode(),
                String.join(",", errors.values()));
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(MyResourceDuplicateException.class)
    public MyValueResponse<?> DuplicateExceptionHandler(MyResourceDuplicateException exc) {
        return MyValueResponse.error(
                String.valueOf(HttpStatus.CONFLICT.value()),
                EErrorCode.RESOURCE_DUPLICATED.getCode(),
                exc.getMessage()
        );
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(MyResourceNotFoundException.class)
    public MyValueResponse<?> NotFoundExceptionHandler(MyResourceNotFoundException exc) {
        return MyValueResponse.error(
                String.valueOf(HttpStatus.NOT_FOUND.value()),
                EErrorCode.RESOURCE_NOT_FOUND.getCode(),
                exc.getMessage()
        );
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MyResourceNotValid.class)
    public MyValueResponse<?> NotValidExceptionHandler(MyResourceNotValid exc) {
        return MyValueResponse.error(
                String.valueOf(HttpStatus.BAD_REQUEST.value()),
                EErrorCode.REQUEST_NOT_VALID.getCode(),
                exc.getMessage()
        );
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(ExpiredJwtException.class)
    public MyValueResponse<?> AccessTokenExceptionHandler(ExpiredJwtException exc) {
        return MyValueResponse.error(
                String.valueOf(HttpStatus.UNAUTHORIZED.value()),
                EErrorCode.TOKEN_EXPIRED.getCode(),
                exc.getMessage()
        );
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public MyValueResponse<?> internalExceptionHandler(Exception exc) {
        return MyValueResponse.error(
                String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()),
                EErrorCode.SERVER_ERROR.getCode(),
                exc.getMessage()
        );
    }
}
