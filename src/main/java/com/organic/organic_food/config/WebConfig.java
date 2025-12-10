package com.organic.organic_food.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;
import java.util.Collections;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    // Dùng @Bean để tạo bộ lọc ưu tiên cao nhất (CORS Filter)
    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();

        // 1. Cho phép thông tin xác thực (Cookie, Auth)
        config.setAllowCredentials(true);

        // 2. Cho phép MỌI domain truy cập (Dùng Pattern để an toàn hơn *)
        config.setAllowedOriginPatterns(Collections.singletonList("*"));

        // 3. Cho phép MỌI loại Header
        config.setAllowedHeaders(Collections.singletonList("*"));

        // 4. Cho phép MỌI phương thức (GET, POST...)
        config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));

        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}
