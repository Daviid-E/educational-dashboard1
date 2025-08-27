package com.example.dashboard.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI apiDoc() {
        return new OpenAPI()
                .info(new Info().title("Educational Dashboard API")
                        .version("1.0.0")
                        .description("Student Risk Assessment & Intervention System"));
    }
}
