package com.organic.organic_food.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.organic.organic_food.entity.Order;
import com.organic.organic_food.entity.OrderStatus;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

	// =================================================================
	// 1. CÁC HÀM TÌM KIẾM CƠ BẢN
	// =================================================================

	// Lấy lịch sử mua hàng của 1 User (Sắp xếp đơn mới nhất lên đầu)
	List<Order> findByUserIdOrderByCreatedAtDesc(Long userId);

	// Lọc đơn hàng theo Trạng thái (VD: Admin muốn xem các đơn PENDING để duyệt)
	List<Order> findByStatus(OrderStatus status);

	// =================================================================
	// 2. HÀM THỐNG KÊ TIỀN (QUAN TRỌNG)
	// =================================================================

	/**
	 * Tính tổng số tiền thực tế User đã chi tiêu. Logic: Chỉ cộng dồn những đơn
	 * hàng đã giao thành công (DELIVERED). Các đơn hủy hoặc đang chờ chưa nên tính
	 * vào doanh thu thực.
	 */
	@Query("SELECT SUM(o.totalMoney) FROM Order o WHERE o.user.id = :userId AND o.status = 'DELIVERED'")
	BigDecimal sumTotalSpentByUserId(Long userId);

	/**
	 * (Tùy chọn) Nếu bạn muốn tính tổng tiền của TẤT CẢ các đơn (kể cả đang chờ)
	 * thì dùng hàm dưới này thay cho hàm trên:
	 */
	// @Query("SELECT SUM(o.totalMoney) FROM Order o WHERE o.user.id = :userId")
	// BigDecimal sumAllMoneyByUserId(Long userId);
}