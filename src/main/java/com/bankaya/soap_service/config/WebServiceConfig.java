package com.bankaya.soap_service.config;


import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.config.annotation.WsConfigurerAdapter;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;

@EnableWs
@Configuration
public class WebServiceConfig {

    @Bean
    public ServletRegistrationBean<MessageDispatcherServlet> messageDispatcherServlet(ApplicationContext applicationContext) {
        MessageDispatcherServlet servlet = new MessageDispatcherServlet();
        servlet.setApplicationContext(applicationContext);
        servlet.setTransformWsdlLocations(true);
        return new ServletRegistrationBean<>(servlet, "/ws/*");
    }

    @Bean(name = "pokemon")
    public DefaultWsdl11Definition defaultWsdl11Definition(XsdSchema pokemonSchema) {
        DefaultWsdl11Definition definition = new DefaultWsdl11Definition();
        definition.setPortTypeName("PokemonPort");
        definition.setLocationUri("/ws");
        definition.setTargetNamespace("http://bankaya/pokemon/api/v1");
        definition.setSchema(pokemonSchema);
        return definition;
    }

    @Bean
    public XsdSchema pokemonSchema() {
        return new SimpleXsdSchema(new ClassPathResource("pokemon.xsd"));
    }
}
