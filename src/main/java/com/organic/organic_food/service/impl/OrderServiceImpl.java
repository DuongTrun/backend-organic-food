package com.organic.organic_food.service.impl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.organic.organic_food.entity.Order;
import com.organic.organic_food.entity.OrderStatus;
import com.organic.organic_food.repository.OrderRepository;
import com.organic.organic_food.service.OrderService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

	private final OrderRepository orderRepository;

	@Override
	public List<Order> getAllOrders() {
		// Lấy tất cả đơn hàng, sắp xếp đơn mới nhất lên đầu
		return orderRepository.findAll(Sort.by(Sort.Direction.DESC, "createdAt"));
	}

	@Override
	public List<Order> getOrdersByUserId(Long userId) {
		return orderRepository.findByUserIdOrderByCreatedAtDesc(userId);
	}

	@Override
	public Order updateOrderStatus(Long orderId, String newStatus) {
		// 1. Tìm đơn hàng
		Order order = orderRepository.findById(orderId)
				.orElseThrow(() -> new RuntimeException("Không tìm thấy đơn hàng có ID: " + orderId));

		// 2. Kiểm tra xem status gửi lên có hợp lệ không (Tránh Admin gửi chữ linh
		// tinh)
		try {
			OrderStatus status = OrderStatus.valueOf(newStatus.toUpperCase());
			order.setStatus(status);
		} catch (IllegalArgumentException e) {
			throw new RuntimeException(
					"Trạng thái không hợp lệ! Chỉ chấp nhận: PENDING, CONFIRMED, SHIPPING, DELIVERED, CANCELLED");
		}

		// 3. Lưu xuống DB
		return orderRepository.save(order);
	}

	@Override
	public BigDecimal getTotalSpentByUser(Long userId) {
		// Gọi Repository để tính tổng
		BigDecimal total = orderRepository.sumTotalSpentByUserId(userId);

		// Nếu chưa mua gì thì trả về 0 thay vì null
		return total != null ? total : BigDecimal.ZERO;
	}
}