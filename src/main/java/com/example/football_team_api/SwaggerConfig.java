package com.example.football_team_api;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Football Team API ⚽")
                        .description("API for managing teams and players of OGC Nice and other teams")
                        .version("1.0.0"));
    }
}
