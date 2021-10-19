package io.github.tn1.server.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
				.allowedMethods("GET", "POST", "DELETE", "PATCH", "PUT")
                .allowedOrigins("http://localhost:3000");
    }
}
