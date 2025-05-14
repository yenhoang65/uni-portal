package io.spring.uni_portal.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig {
    @Bean
    public WebMvcConfigurer webMvcConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("http://localhost:3000")  // Chỉ định cụ thể domain
                        .allowCredentials(true)                   // Cho phép gửi cookies
                        .allowedMethods("GET", "POST", "PUT", "DELETE") // Các phương thức được phép
                        .allowedHeaders("*");
            }
        };
    }
}
