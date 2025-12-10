package com.organic.organic_food.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**").allowedOriginPatterns("*") // Cho phép mọi domain truy cập
				.allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Cho phép mọi thao tác
				.allowedHeaders("*") // Cho phép mọi loại dữ liệu đầu vào
				.allowCredentials(true); // Cho phép gửi cookie/xác thực (nếu cần)
	}
}
