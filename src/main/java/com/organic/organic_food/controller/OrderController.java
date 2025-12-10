package com.organic.organic_food.controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.organic.organic_food.entity.Order;
import com.organic.organic_food.service.OrderService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173") // Cho phép Frontend (Vite) truy cập
public class OrderController {

	private final OrderService orderService;

	// =================================================================
	// 1. LẤY DANH SÁCH ĐƠN HÀNG
	// =================================================================

	// API: GET /api/orders
	// Tác dụng: Lấy tất cả đơn hàng (Dùng cho Admin Dashboard)
	@GetMapping
	public List<Order> getAllOrders() {
		return orderService.getAllOrders();
	}

	// API: GET /api/orders/user/{userId}
	// Tác dụng: Lấy lịch sử mua hàng của 1 user cụ thể
	@GetMapping("/user/{userId}")
	public List<Order> getOrdersByUserId(@PathVariable Long userId) {
		return orderService.getOrdersByUserId(userId);
	}

	// =================================================================
	// 2. CẬP NHẬT TRẠNG THÁI (ADMIN)
	// =================================================================

	// API: PUT /api/orders/{id}/status?status=SHIPPING
	// Tác dụng: Admin đổi trạng thái đơn hàng
	// Các trạng thái hợp lệ: PENDING, CONFIRMED, SHIPPING, DELIVERED, CANCELLED
	@PutMapping("/{id}/status")
	public ResponseEntity<Order> updateOrderStatus(@PathVariable Long id, @RequestParam String status // Nhận status từ
																										// param trên
																										// URL
	) {
		Order updatedOrder = orderService.updateOrderStatus(id, status);
		return ResponseEntity.ok(updatedOrder);
	}

	// =================================================================
	// 3. THỐNG KÊ TÀI CHÍNH
	// =================================================================

	// API: GET /api/orders/user/{userId}/total-spent
	// Tác dụng: Xem user này đã mua hết bao nhiêu tiền (Chỉ tính đơn thành công)
	@GetMapping("/user/{userId}/total-spent")
	public ResponseEntity<Map<String, Object>> getUserTotalSpent(@PathVariable Long userId) {
		BigDecimal total = orderService.getTotalSpentByUser(userId);

		// Trả về JSON: { "userId": 1, "totalSpent": 500000 }
		return ResponseEntity.ok(Map.of("userId", userId, "totalSpent", total));
	}
}