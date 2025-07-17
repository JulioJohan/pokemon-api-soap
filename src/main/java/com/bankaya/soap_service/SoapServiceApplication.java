package com.bankaya.soap_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;


@EnableFeignClients
@SpringBootApplication
public class SoapServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SoapServiceApplication.class, args);
	}

}
