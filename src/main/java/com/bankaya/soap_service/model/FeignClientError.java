package com.bankaya.soap_service.model;


import lombok.Data;

@Data
public class FeignClientError {

    private String httpStatus;
    private String errorCode;
    private String errorDescription;
}
