package com.bankaya.soap_service.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

//    @ExceptionHandler(NotFoundException.class)
//    @ResponseBody
//    public NotFoundResponse handleNotFoundException(NotFoundException exception){
//        NotFoundResponse response = new NotFoundResponse();
//        response.setMessage(exception.getMessage());
//        response.setStatus(exception.getHttpStatus());
//        return response;
//    }
}
