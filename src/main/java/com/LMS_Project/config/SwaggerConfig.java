package com.LMS_Project.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI lmsOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Learning Management System API")
                        .description("REST API for managing students, instructors, courses, and enrollments")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("LMS Team")
                                .email("lms@example.com")));
    }
}
