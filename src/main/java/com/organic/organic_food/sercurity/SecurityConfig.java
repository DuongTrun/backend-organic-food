//package com.organic.organic_food.sercurity; // Check lại package name
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.HttpMethod;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter; // Import cái này
//
//import lombok.RequiredArgsConstructor;
//
//@Configuration
//@RequiredArgsConstructor // Lombok sẽ tự tạo Constructor để inject biến final bên dưới
//public class SecurityConfig {
//
//	// 👉 1. Khai báo biến filter ở đây để Spring Inject vào
//	private final JwtFilter jwtFilter;
//
//	@Bean
//	public PasswordEncoder passwordEncoder() {
//		return new BCryptPasswordEncoder();
//	}
//
//	@Bean
//	public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
//		return config.getAuthenticationManager();
//	}
//
//	@Bean
//	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//		http.csrf(csrf -> csrf.disable()).authorizeHttpRequests(auth -> auth
//				// Public các API lấy sản phẩm
//				.requestMatchers(HttpMethod.GET, "/api/products/**").permitAll().requestMatchers("/api/auth/**")
//				.permitAll()
//
//				// Các API Admin cần quyền (hoặc ít nhất là đã login)
//				.requestMatchers(HttpMethod.POST, "/api/products").authenticated()
//				.requestMatchers(HttpMethod.PUT, "/api/products/**").authenticated()
//				.requestMatchers(HttpMethod.DELETE, "/api/products/**").authenticated()
//				.requestMatchers("/api/orders/**").authenticated()
//
//				.anyRequest().authenticated())
//				.sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//
//				// 👉 2. Sử dụng biến jwtFilter (chữ thường) đã inject ở trên
//				.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
//
//		return http.build();
//	}
//}

package com.organic.organic_food.sercurity;

import java.util.Arrays; // Import mới
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration; // Import mới
import org.springframework.web.cors.CorsConfigurationSource; // Import mới
import org.springframework.web.cors.UrlBasedCorsConfigurationSource; // Import mới

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

	private final JwtFilter jwtFilter;

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
		return config.getAuthenticationManager();
	}

	// 🔥 CẤU HÌNH CORS CHO TOÀN BỘ HỆ THỐNG
	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();

		// 1. Cho phép Frontend (React) truy cập
		configuration.setAllowedOrigins(List.of("*"));

		// 2. Cho phép các Method này
		configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));

		// 3. Cho phép gửi Header (nhất là Authorization để chứa Token)
		configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type", "X-Requested-With"));

		// 4. Cho phép gửi credentials (nếu cần cookie)
		configuration.setAllowCredentials(true);

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
				// 👉 Bật cấu hình CORS ở đây
				.cors(cors -> cors.configurationSource(corsConfigurationSource()))

				.csrf(csrf -> csrf.disable())
				.authorizeHttpRequests(auth -> auth.requestMatchers(HttpMethod.GET, "/api/products/**").permitAll()
						.requestMatchers("/api/auth/**").permitAll()

						// Cho phép request OPTIONS (Preflight) đi qua
						.requestMatchers(HttpMethod.OPTIONS, "/**").permitAll().requestMatchers("/api/payment/**")
						.permitAll().requestMatchers(HttpMethod.POST, "/api/products").authenticated()
						.requestMatchers(HttpMethod.PUT, "/api/products/**").authenticated()
						.requestMatchers(HttpMethod.DELETE, "/api/products/**").authenticated()
						.requestMatchers("/api/chatbot/**").permitAll()
						.requestMatchers("/api/orders/**").authenticated()

						.anyRequest().authenticated())
				.sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

		return http.build();
	}

}
