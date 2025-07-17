package com.bankaya.soap_service.exception;


import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class NotFoundException extends RuntimeException {

    private final HttpStatus httpStatus;

    private final String message;

    public NotFoundException(HttpStatus httpStatus, String message) {
        super(message);
        this.httpStatus = httpStatus;
        this.message = message;
    }
}
