package com.organic.organic_food.service;

import java.math.BigDecimal;
import java.util.List;

import com.organic.organic_food.entity.Order;

public interface OrderService {

	// 1. Lấy tất cả đơn hàng (Dành cho Admin dashboard)
	List<Order> getAllOrders();

	// 2. Lấy lịch sử đơn hàng của 1 User cụ thể (Dành cho User xem lịch sử)
	List<Order> getOrdersByUserId(Long userId);

	// 3. Cập nhật trạng thái đơn hàng (Admin duyệt đơn: Pending -> Shipping ->
	// Delivered)
	Order updateOrderStatus(Long orderId, String newStatus);

	// 4. Tính tổng số tiền User đã tiêu (Dùng để hiển thị thống kê)
	BigDecimal getTotalSpentByUser(Long userId);
}