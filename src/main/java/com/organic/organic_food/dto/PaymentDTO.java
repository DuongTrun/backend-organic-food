package com.organic.organic_food.dto;

import java.util.List;

import lombok.Data;

@Data
public class PaymentDTO {
	// 1. Thông tin người mua (Để lưu vào bảng Order)
	private Long userId; // Có thể null nếu mua không cần đăng nhập
	private String fullName;
	private String phoneNumber;
	private String address;
	private String note;

	// 2. Thông tin thanh toán (Để gửi sang VNPay)
	private Long amount; // Tổng tiền (VNĐ)
	private String orderInfo; // Ví dụ: "Thanh toan don hang #123"

	// 3. Chi tiết đơn hàng (QUAN TRỌNG: Để lưu vào bảng OrderDetails)
	private List<CartItemDTO> items;

	// Class con để hứng dữ liệu từng món hàng từ giỏ hàng gửi lên
	@Data
	public static class CartItemDTO {
		private Long productId;
		private String productName;
		private Integer quantity;
		private Double price;
	}
}