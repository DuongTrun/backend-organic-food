//package com.organic.organic_food.repository;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import org.springframework.data.jpa.domain.Specification;
//
//import com.organic.organic_food.entity.Product;
//
//import jakarta.persistence.criteria.Predicate;
//
//public class ProductSpecification {
//
//	public static Specification<Product> filterBy(String origin, Boolean isOrganic, List<String> statuses) {
//		return (root, query, criteriaBuilder) -> {
//			List<Predicate> predicates = new ArrayList<>();
//
//			// Thêm điều kiện lọc cho nguồn gốc (origin)
//			if (origin != null && !origin.trim().isEmpty()) {
//				predicates.add(criteriaBuilder.equal(root.get("origin"), origin));
//			}
//
//			// Thêm điều kiện lọc cho hữu cơ (isOrganic)
//			if (isOrganic != null) {
//				predicates.add(criteriaBuilder.equal(root.get("isOrganic"), isOrganic));
//			}
//
//			// Thêm điều kiện lọc cho trạng thái (processingStatus)
//			if (statuses != null && !statuses.isEmpty()) {
//				predicates.add(root.get("processingStatus").in(statuses));
//			}
//
//			return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
//		};
//	}
//}

package com.organic.organic_food.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.organic.organic_food.entity.Product;

import jakarta.persistence.criteria.Predicate;

public class ProductSpecification {

	// ✅ Cập nhật tham số: Nhận vào cả CŨ và MỚI
	public static Specification<Product> filterBy(List<String> brands, // Mới (Thương hiệu)
			List<String> categories, // Mới (Danh mục)
			Double minPrice, // Mới (Giá min)
			Double maxPrice, // Mới (Giá max)
			String origin, // Cũ
			Boolean isOrganic, // Cũ
			List<String> statuses // Cũ
	) {
		return (root, query, criteriaBuilder) -> {
			List<Predicate> predicates = new ArrayList<>();

			// ============================================
			// 🆕 PHẦN MỚI THÊM VÀO (Brand, Category, Price)
			// ============================================

			// 1. Lọc Brand
			if (brands != null && !brands.isEmpty()) {
				predicates.add(root.get("brand").in(brands));
			}

			// 2. Lọc Category
			if (categories != null && !categories.isEmpty()) {
				predicates.add(root.get("category").in(categories));
			}

			// 3. Lọc Giá (Min)
			if (minPrice != null) {
				predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("price"), minPrice));
			}

			// 4. Lọc Giá (Max)
			if (maxPrice != null) {
				predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("price"), maxPrice));
			}

			// ============================================
			// 🔙 PHẦN CŨ CỦA BẠN (GIỮ NGUYÊN)
			// ============================================

			// Lọc nguồn gốc (origin)
			if (origin != null && !origin.trim().isEmpty()) {
				predicates.add(criteriaBuilder.equal(root.get("origin"), origin));
			}

			// Lọc hữu cơ (isOrganic)
			if (isOrganic != null) {
				predicates.add(criteriaBuilder.equal(root.get("isOrganic"), isOrganic));
			}

			// Lọc trạng thái (processingStatus)
			if (statuses != null && !statuses.isEmpty()) {
				predicates.add(root.get("processingStatus").in(statuses));
			}

			return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
		};
	}
}