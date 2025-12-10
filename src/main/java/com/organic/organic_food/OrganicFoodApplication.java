package com.organic.organic_food;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class OrganicFoodApplication {

	public static void main(String[] args) {

		// ===================================================================
		// ✅ THÊM DÒNG NÀY VÀO NGAY ĐÂY ĐỂ KIỂM TRA
		String googleCredentials = System.getenv("GOOGLE_APPLICATION_CREDENTIALS");
		System.out.println("===== KIỂM TRA BIẾN MÔI TRƯỜNG =====");
		System.out.println("GOOGLE_APPLICATION_CREDENTIALS = " + googleCredentials);
		System.out.println("======================================");
		// ===================================================================
		SpringApplication.run(OrganicFoodApplication.class, args);
	}

}
