package com.organic.organic_food.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.organic.organic_food.dto.LoginRequest;
import com.organic.organic_food.dto.RegisterRequest;
import com.organic.organic_food.entity.User;
import com.organic.organic_food.repository.UserRepository;
import com.organic.organic_food.sercurity.JwtUtil;

@Service
public class AuthService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final JwtUtil jwtUtil;

	public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
		this.jwtUtil = jwtUtil;
	}

	public String register(RegisterRequest request) {
		if (userRepository.findByEmail(request.getEmail()).isPresent()) {
			throw new RuntimeException("Email đã tồn tại");
		}

		User user = User.builder().username(request.getUsername()).email(request.getEmail())
				.password(passwordEncoder.encode(request.getPassword())).role("USER").build();

		userRepository.save(user);
		return "Đăng ký thành công!";
	}

	public Map<String, Object> login(LoginRequest request) {
		User user = userRepository.findByEmail(request.getEmail())
				.orElseThrow(() -> new RuntimeException("Email không tồn tại"));
		System.out.println("=== DEBUG LOGIN ===");
		System.out.println("1. Email: " + user.getEmail());
		System.out.println("2. Password nhập vào: " + request.getPassword());
		System.out.println("3. Password trong DB: " + user.getPassword());
		System.out.println("4. Độ dài Pass DB: " + user.getPassword().length());
		boolean check = passwordEncoder.matches(request.getPassword(), user.getPassword());
		System.out.println("5. Kết quả so sánh matches(): " + check);
		System.out.println("===================");
		if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
			throw new RuntimeException("Sai mật khẩu");
		}

		String token = jwtUtil.generateToken(user.getEmail());

		Map<String, Object> response = new HashMap<>();
		response.put("token", token);
		response.put("username", user.getUsername()); // thêm username hoặc fullname nếu có

		return response;
	}
}