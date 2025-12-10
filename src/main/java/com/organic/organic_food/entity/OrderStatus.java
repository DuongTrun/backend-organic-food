package com.organic.organic_food.entity;

public enum OrderStatus {
	PENDING, // Chờ xử lý (Mặc định khi mới đặt)
	CONFIRMED, // Admin đã xác nhận đơn
	SHIPPING, // Đang giao hàng
	DELIVERED, // Đã giao thành công
	CANCELLED // Đã hủy
}