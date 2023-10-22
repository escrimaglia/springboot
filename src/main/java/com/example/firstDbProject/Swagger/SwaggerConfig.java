package com.example.firstDbProject.Swagger;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@SpringBootApplication
public class SwaggerConfig {

    @Bean
    public OpenAPI custom() {
        return new OpenAPI().info(new Info()
                        .title("Documentación API")
                        .description("BackEnd Applications UTN FRC")
                        .version("1.0"));
    }
}

