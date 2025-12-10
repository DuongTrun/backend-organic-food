package com.organic.organic_food.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {
	// --- GIỮ NGUYÊN CÁC TRƯỜNG CŨ ---
	private Long id;
	private String name;
	private Double price;
	private String image;
	private String slug;
	private String description;
	private String category;

	// --- CÁC TRƯỜNG MỚI BẠN ĐÃ THÊM ---
	private String origin; // vietnam, import
	private String processingStatus; // fresh, dried, frozen

	// Lưu ý: Entity dùng isOrganic, DTO dùng organic (thường FE thích tên ngắn gọn
	// này hơn)
	private boolean organic;

	// Lưu ý: Entity dùng isFeatured, DTO dùng featured
	private boolean featured;

	// 🔥 MỚI THÊM: Cần thiết để hiển thị tên thương hiệu (Organicfood.vn, Thiên
	// Nhiên...)
	private String brand;
}