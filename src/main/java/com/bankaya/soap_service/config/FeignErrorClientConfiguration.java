package com.bankaya.soap_service.config;

import com.bankaya.soap_service.model.FeignClientError;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FeignClientProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;

import java.io.IOException;


@Slf4j
public class FeignErrorClientConfiguration extends FeignClientProperties.FeignClientConfiguration {


    @Bean
    public ErrorDecoder errorDecoder(){
        return (methodKey,response) -> {
          if (HttpStatus.valueOf(response.status()).is4xxClientError()
                  || HttpStatus.valueOf(response.status()).is5xxServerError()){
              log.error("error feign client with status {}, in the request {}", response.status() , response.request().url());
              FeignClientError feignClientError = new FeignClientError();
              String responseBody;
                try {
                    responseBody = response.body() != null
                            ? response.body().asInputStream().toString()
                            : "No response body";
                    feignClientError.setHttpStatus(String.valueOf(response.status()));
                    feignClientError.setErrorCode(String.valueOf(response.status()));
                    feignClientError.setErrorDescription(responseBody);
                } catch (IOException e) {
                    log.error("Failed to read response body", e);
                    feignClientError.setErrorDescription("Failed to read response body");
                }
            }
          return new ErrorDecoder.Default().decode(methodKey,response);
        };
    }

}
