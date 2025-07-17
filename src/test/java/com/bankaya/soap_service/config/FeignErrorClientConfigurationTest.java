package com.bankaya.soap_service.config;


import feign.Request;
import feign.RequestTemplate;
import feign.Response;
import feign.codec.ErrorDecoder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FeignErrorClientConfigurationTest {


      @InjectMocks
    private FeignErrorClientConfiguration feignErrorClientConfiguration;

    @Mock
    private ErrorDecoder errorDecoder;

    @Mock
    private Response responseMock;

    @Mock
    private Response.Body bodyMock;



    @Test
    void getErrorDecoderTest(){
        Request request = Request.create(Request.HttpMethod.GET, "http://example.com",
                new HashMap<>(), null, Charset.defaultCharset(), new RequestTemplate());

        String responseBody = "{\"error\":\"Internal Server Error\"}";
        Response response = Response.builder().status(500)
                .body(responseBody, Charset.defaultCharset())
                .request(request).build();

        ErrorDecoder decoder = feignErrorClientConfiguration.errorDecoder();
        assertNotNull(decoder);
        decoder.decode("GET",response);
    }


    @Test
    void getErrorDecoderCathExceptionTest() throws IOException {
        Request request = Request.create(Request.HttpMethod.GET, "http://example.com",
                new HashMap<>(), null, null, null);

        when(responseMock.status()).thenReturn(500);
        when(responseMock.request()).thenReturn(request);

        when(responseMock.body()).thenReturn(bodyMock);
        doThrow(new IOException("Failed to read response body")).when(bodyMock).asInputStream();

        ErrorDecoder decoder = feignErrorClientConfiguration.errorDecoder();
        assertNotNull(decoder);
        decoder.decode("GET",responseMock);
    }

}
