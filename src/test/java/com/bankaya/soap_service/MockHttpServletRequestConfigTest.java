package com.bankaya.soap_service;


import jakarta.servlet.http.HttpServletRequest;
import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class MockHttpServletRequestConfigTest {

    @Bean
    public HttpServletRequest httpServletRequest() {
        return Mockito.mock(HttpServletRequest.class);
    }
}
