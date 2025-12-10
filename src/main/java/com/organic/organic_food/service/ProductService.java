//package com.organic.organic_food.service;
//
//import java.util.List;
//
//import com.organic.organic_food.dto.ProductDTO;
//
//public interface ProductService {
//	List<ProductDTO> searchProducts(String keyword);
//
//	List<ProductDTO> getAllProducts();
//
//	ProductDTO getProductById(Long id);
//
//	// 👉 Thêm các phương thức mới
//	List<ProductDTO> getProductsByCategory(String category);
//
//	List<ProductDTO> getFeaturedProducts();
//}

//package com.organic.organic_food.service;
//
//import java.util.List;
//
//import com.organic.organic_food.dto.ProductDTO;
//
//public interface ProductService {
//	// ✅ PHƯƠNG THỨC MỚI CHO VIỆC LỌC SẢN PHẨM
//	List<ProductDTO> getFilteredProducts(String origin, Boolean isOrganic, List<String> processingStatus);
//
//	// Các phương thức cũ giữ nguyên
//	List<ProductDTO> searchProducts(String keyword);
//
//	List<ProductDTO> getAllProducts();
//
//	ProductDTO getProductById(Long id);
//
//	List<ProductDTO> getProductsByCategory(String category);
//
//	List<ProductDTO> getFeaturedProducts();
//}

//package com.organic.organic_food.service;
//
//import java.util.List;
//
//import org.springframework.stereotype.Service;
//
//import com.organic.organic_food.entity.Product;
//import com.organic.organic_food.repository.ProductRepository;
//
//@Service
//public class ProductService {
//
//	private final ProductRepository productRepository;
//
//	public ProductService(ProductRepository productRepository) {
//		this.productRepository = productRepository;
//	}
//
//	// Lấy tất cả sản phẩm
//	public List<Product> getAllProducts() {
//		return productRepository.findAll();
//	}
//
//	// Tìm kiếm sản phẩm theo tên
//	public List<Product> searchProducts(String keyword) {
//		if (keyword == null || keyword.isEmpty()) {
//			return productRepository.findAll();
//		}
//		return productRepository.findByNameContainingIgnoreCase(keyword);
//	}
//}
package com.organic.organic_food.service;

import java.util.List;

import com.organic.organic_food.dto.ProductDTO;

public interface ProductService {

	// =================================================================
	// 1. CÁC PHƯƠNG THỨC DÀNH CHO KHÁCH HÀNG (GIỮ NGUYÊN)
	// =================================================================

	// Hàm lọc sản phẩm nâng cao (Filter)
	List<ProductDTO> getFilteredProducts(List<String> brands, List<String> categories, Double minPrice, Double maxPrice,
			String origin, Boolean isOrganic, List<String> processingStatus);

	List<ProductDTO> searchProducts(String keyword);

	List<ProductDTO> getAllProducts();

	ProductDTO getProductById(Long id);

	List<ProductDTO> getProductsByCategory(String category);

	List<ProductDTO> getFeaturedProducts();

	// =================================================================
	// 2. CÁC PHƯƠNG THỨC MỚI DÀNH CHO ADMIN (THÊM - SỬA - XÓA)
	// =================================================================

	ProductDTO createProduct(ProductDTO dto);

	ProductDTO updateProduct(Long id, ProductDTO dto);

	void deleteProduct(Long id);
}