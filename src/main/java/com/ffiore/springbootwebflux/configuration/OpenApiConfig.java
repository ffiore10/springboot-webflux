package com.ffiore.springbootwebflux.configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenApi(){

        return new OpenAPI()
                .components(new Components())
                .info(new Info()
                        .contact(new Contact().name("Francesco Fiore").email("flower@gmail.com"))
                        .description("Spring BOOT WebFlux APIs documentation")
                        .version("1.0")
                );
    }
}
