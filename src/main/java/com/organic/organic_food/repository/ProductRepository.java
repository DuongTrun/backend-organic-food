package com.organic.organic_food.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.organic.organic_food.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {

	// =================================================================
	// 1. CÁC PHƯƠNG THỨC CŨ (GIỮ NGUYÊN 100%)
	// =================================================================

	// Tìm kiếm theo từ khóa trong tên (không phân biệt hoa thường)
	List<Product> findByNameContainingIgnoreCase(String keyword);

	// Tìm theo danh mục chính xác
	List<Product> findByCategory(String category);

	// Lấy danh sách sản phẩm nổi bật
	List<Product> findByIsFeaturedTrue();

	// 💡 Note: Các hàm lọc phức tạp sẽ dùng JpaSpecificationExecutor.findAll(spec)

	// =================================================================
	// 2. CÁC PHƯƠNG THỨC MỚI HỖ TRỢ ADMIN (VALIDATION)
	// =================================================================

	// Kiểm tra xem tên sản phẩm đã tồn tại chưa (Dùng để báo lỗi khi Admin thêm
	// trùng tên)
	boolean existsByName(String name);

	// Kiểm tra slug (để đảm bảo đường dẫn URL không bị trùng)
	boolean existsBySlug(String slug);
}