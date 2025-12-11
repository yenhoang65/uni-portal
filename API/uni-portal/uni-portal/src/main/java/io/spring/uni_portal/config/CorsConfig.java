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
                        .allowedOrigins("http://localhost:3000",
                                "http://uniportal.site/api",
                                "https://ui-uni.vercel.app",
                                "https://uniportal-utehy.site",
                                "https://uniportal-utehy.site/api",
                                "http://uniportal-utehy.site",
                                "http://uniportal-utehy.site/api",
                                "https://uniportal-utehy.website",
                                "https://www.uniportal-utehy.website",
                                "https://uniportal.site/api")  // Chỉ định cụ thể domain
                        .allowCredentials(true)                   // Cho phép gửi cookies
                        .allowedMethods("GET", "POST", "PUT", "DELETE") // Các phương thức được phép
                        .allowedHeaders("*");
//                http://localhost:3000 
            }
        };
    }
}

//https://www.uniportal-utehy.website/login