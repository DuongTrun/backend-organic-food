////package com.organic.organic_food.service;
////
////import java.util.List;
////import java.util.stream.Collectors;
////
////import org.springframework.stereotype.Service;
////
////import com.organic.organic_food.dto.ProductDTO;
////import com.organic.organic_food.entity.Product;
////import com.organic.organic_food.exception.ResourceNotFoundException;
////import com.organic.organic_food.repository.ProductRepository;
////
////@Service
////public class ProductServiceImpl implements ProductService {
////
////	private final ProductRepository productRepository;
////
////	public ProductServiceImpl(ProductRepository productRepository) {
////		this.productRepository = productRepository;
////	}
////
////	@Override
////	public List<ProductDTO> searchProducts(String keyword) {
////		List<Product> products;
////		if (keyword == null || keyword.trim().isEmpty()) {
////			products = productRepository.findAll();
////		} else {
////			products = productRepository.findByNameContainingIgnoreCase(keyword);
////		}
////
////		return products.stream().map(this::mapToDTO).collect(Collectors.toList());
////	}
////
////	@Override
////	public List<ProductDTO> getAllProducts() {
////		return productRepository.findAll().stream().map(this::mapToDTO).collect(Collectors.toList());
////	}
////
////	@Override
////	public ProductDTO getProductById(Long id) {
////		Product product = productRepository.findById(id)
////				.orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy sản phẩm có id = " + id));
////		return mapToDTO(product);
////	}
////
////	private ProductDTO mapToDTO(Product product) {
////		return ProductDTO.builder().id(product.getId()).name(product.getName()).description(product.getDescription())
////				.price(product.getPrice()).image(product.getImage()).build();
////	}
////}
//
//package com.organic.organic_food.service;
//
//import java.util.List;
//import java.util.stream.Collectors;
//
//import org.springframework.stereotype.Service;
//
//import com.organic.organic_food.dto.ProductDTO;
//import com.organic.organic_food.entity.Product;
//import com.organic.organic_food.exception.ResourceNotFoundException;
//import com.organic.organic_food.repository.ProductRepository;
//
//@Service
//public class ProductServiceImpl implements ProductService {
//
//	private final ProductRepository productRepository;
//
//	public ProductServiceImpl(ProductRepository productRepository) {
//		this.productRepository = productRepository;
//	}
//
//	// 🔍 Tìm kiếm sản phẩm theo tên
//	@Override
//	public List<ProductDTO> searchProducts(String keyword) {
//		List<Product> products;
//		if (keyword == null || keyword.trim().isEmpty()) {
//			products = productRepository.findAll();
//		} else {
//			products = productRepository.findByNameContainingIgnoreCase(keyword);
//		}
//
//		return products.stream().map(this::mapToDTO).collect(Collectors.toList());
//	}
//
//	// 📦 Lấy tất cả sản phẩm
//	@Override
//	public List<ProductDTO> getAllProducts() {
//		return productRepository.findAll().stream().map(this::mapToDTO).collect(Collectors.toList());
//	}
//
//	// 🔎 Lấy sản phẩm theo ID
//	@Override
//	public ProductDTO getProductById(Long id) {
//		Product product = productRepository.findById(id)
//				.orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy sản phẩm có id = " + id));
//		return mapToDTO(product);
//	}
//
//	// 🧩 Chuyển từ Entity sang DTO
//	private ProductDTO mapToDTO(Product product) {
//		return ProductDTO.builder().id(product.getId()).name(product.getName()).description(product.getDescription())
//				.price(product.getPrice()).image(product.getImage()).build(); // ✅ thiếu dòng này là lỗi
//	}
//}

//package com.organic.organic_food.service;
//
//import java.util.List;
//import java.util.stream.Collectors;
//
//import org.springframework.stereotype.Service;
//
//import com.organic.organic_food.dto.ProductDTO;
//import com.organic.organic_food.entity.Product;
//import com.organic.organic_food.repository.ProductRepository;
//
//import lombok.RequiredArgsConstructor;
//
//@Service
//@RequiredArgsConstructor
//public class ProductServiceImpl implements ProductService {
//
//	private final ProductRepository productRepository;
//
//	private ProductDTO convertToDTO(Product product) {
//		ProductDTO dto = new ProductDTO();
//		dto.setId(product.getId());
//		dto.setName(product.getName());
//		dto.setPrice(product.getPrice());
//		dto.setImage(product.getImage());
//		dto.setCategory(product.getCategory());
//		dto.setFeatured(product.isFeatured());
//		dto.setDescription(product.getDescription());
//		return dto;
//	}
//
//	@Override
//	public List<ProductDTO> searchProducts(String keyword) {
//		return productRepository.findByNameContainingIgnoreCase(keyword).stream().map(this::convertToDTO)
//				.collect(Collectors.toList());
//	}
//
//	@Override
//	public List<ProductDTO> getAllProducts() {
//		return productRepository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
//	}
//
//	@Override
//	public ProductDTO getProductById(Long id) {
//		return productRepository.findById(id).map(this::convertToDTO).orElse(null);
//	}
//
//	@Override
//	public List<ProductDTO> getProductsByCategory(String category) {
//		return productRepository.findByCategory(category).stream().map(this::convertToDTO).collect(Collectors.toList());
//	}
//
//	@Override
//	public List<ProductDTO> getFeaturedProducts() {
//		return productRepository.findByIsFeaturedTrue().stream().map(this::convertToDTO).collect(Collectors.toList());
//	}
//}
//
//package com.organic.organic_food.service;
//
//import java.util.List;
//import java.util.stream.Collectors;
//
//import org.springframework.data.jpa.domain.Specification; // ✅ IMPORT MỚI
//import org.springframework.stereotype.Service;
//
//import com.organic.organic_food.dto.ProductDTO;
//import com.organic.organic_food.entity.Product;
//import com.organic.organic_food.repository.ProductRepository;
//import com.organic.organic_food.repository.ProductSpecification; // ✅ IMPORT MỚI
//
//import lombok.RequiredArgsConstructor;
//
//@Service
//@RequiredArgsConstructor
//public class ProductServiceImpl implements ProductService {
//
//	private final ProductRepository productRepository;
//
//	private ProductDTO convertToDTO(Product product) {
//		ProductDTO dto = new ProductDTO();
//		dto.setId(product.getId());
//		dto.setName(product.getName());
//		dto.setPrice(product.getPrice());
//		dto.setImage(product.getImage());
//		dto.setCategory(product.getCategory());
//		dto.setDescription(product.getDescription());
//		dto.setFeatured(product.isFeatured());
//
//		// ✅ ÁNH XẠ CÁC TRƯỜNG MỚI
//		dto.setOrigin(product.getOrigin());
//		dto.setOrganic(product.isOrganic());
//		dto.setProcessingStatus(product.getProcessingStatus());
//
//		return dto;
//	}
//
//	// ✅ TRIỂN KHAI PHƯƠNG THỨC LỌC MỚI
//	@Override
//	public List<ProductDTO> getFilteredProducts(String origin, Boolean isOrganic, List<String> processingStatus) {
//		Specification<Product> spec = ProductSpecification.filterBy(origin, isOrganic, processingStatus);
//		return productRepository.findAll(spec).stream().map(this::convertToDTO).collect(Collectors.toList());
//	}
//
//	// ==========================================================
//	// CÁC PHƯƠNG THỨC CŨ VẪN HOẠT ĐỘNG BÌNH THƯỜNG
//	// ==========================================================
//	@Override
//	public List<ProductDTO> searchProducts(String keyword) {
//		return productRepository.findByNameContainingIgnoreCase(keyword).stream().map(this::convertToDTO)
//				.collect(Collectors.toList());
//	}
//
//	@Override
//	public List<ProductDTO> getAllProducts() {
//		return productRepository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
//	}
//
//	@Override
//	public ProductDTO getProductById(Long id) {
//		return productRepository.findById(id).map(this::convertToDTO).orElse(null);
//	}
//
//	@Override
//	public List<ProductDTO> getProductsByCategory(String category) {
//		return productRepository.findByCategory(category).stream().map(this::convertToDTO).collect(Collectors.toList());
//	}
//
//	@Override
//	public List<ProductDTO> getFeaturedProducts() {
//		return productRepository.findByIsFeaturedTrue().stream().map(this::convertToDTO).collect(Collectors.toList());
//	}
//}
package com.organic.organic_food.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; // Thêm Transactional để đảm bảo an toàn dữ liệu

import com.organic.organic_food.dto.ProductDTO;
import com.organic.organic_food.entity.Product;
import com.organic.organic_food.repository.ProductRepository;
import com.organic.organic_food.repository.ProductSpecification;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

	private final ProductRepository productRepository;

	// =================================================================
	// 1. CÁC HÀM TIỆN ÍCH (HELPER)
	// =================================================================

	// [GIỮ NGUYÊN] Hàm convert từ Entity sang DTO (Dùng để trả về FE)
	private ProductDTO convertToDTO(Product product) {
		ProductDTO dto = new ProductDTO();
		dto.setId(product.getId());
		dto.setName(product.getName());
		dto.setPrice(product.getPrice());
		dto.setSlug(product.getSlug());
		dto.setImage(product.getImage());
		dto.setCategory(product.getCategory());
		dto.setDescription(product.getDescription());
		dto.setFeatured(product.isFeatured());

		// Map thêm các trường mới
		dto.setBrand(product.getBrand());
		dto.setOrigin(product.getOrigin());
		dto.setOrganic(product.isOrganic());
		dto.setProcessingStatus(product.getProcessingStatus());
		return dto;
	}

	// [THÊM MỚI] Hàm map từ DTO sang Entity (Dùng khi Thêm/Sửa)
	private void mapToEntity(Product product, ProductDTO dto) {
		product.setName(dto.getName());
		product.setPrice(dto.getPrice());
		product.setImage(dto.getImage());
		product.setDescription(dto.getDescription());
		product.setCategory(dto.getCategory());
		product.setBrand(dto.getBrand());
		product.setOrigin(dto.getOrigin());
		product.setOrganic(dto.isOrganic()); // Lưu ý: DTO là isOrganic, Entity setOrganic
		product.setProcessingStatus(dto.getProcessingStatus());
		product.setFeatured(dto.isFeatured());

		// Tự động tạo slug từ tên nếu chưa có hoặc tên thay đổi
		if (product.getSlug() == null || !product.getName().equals(dto.getName())) {
			product.setSlug(toSlug(dto.getName()));
		}
	}

	// [THÊM MỚI] Hàm tạo slug đơn giản: "Táo Envy" -> "tao-envy"
	private String toSlug(String input) {
		if (input == null)
			return "";
		return input.toLowerCase().replaceAll("[áàảãạăắằẳẵặâấầẩẫậ]", "a").replaceAll("[éèẻẽẹêếềểễệ]", "e")
				.replaceAll("[iíìỉĩị]", "i").replaceAll("[óòỏõọôốồổỗộơớờởỡợ]", "o").replaceAll("[úùủũụưứừửữự]", "u")
				.replaceAll("[ýỳỷỹỵ]", "y").replaceAll("[đ]", "d").replaceAll("[^a-z0-9\\s-]", "") // Bỏ ký tự đặc biệt
				.trim().replaceAll("\\s+", "-");
	}

	// =================================================================
	// 2. CÁC HÀM GET DỮ LIỆU CŨ (GIỮ NGUYÊN 100%)
	// =================================================================

	@Override
	public List<ProductDTO> getFilteredProducts(List<String> brands, List<String> categories, Double minPrice,
			Double maxPrice, String origin, Boolean isOrganic, List<String> processingStatus) {

		// [GIỮ NGUYÊN] Logic gọi Specification cũ của bạn
		Specification<Product> spec = ProductSpecification.filterBy(brands, categories, minPrice, maxPrice, origin,
				isOrganic, processingStatus);

		return productRepository.findAll(spec).stream().map(this::convertToDTO).collect(Collectors.toList());
	}

	@Override
	public List<ProductDTO> searchProducts(String keyword) {
		return productRepository.findByNameContainingIgnoreCase(keyword).stream().map(this::convertToDTO)
				.collect(Collectors.toList());
	}

	@Override
	public List<ProductDTO> getAllProducts() {
		return productRepository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
	}

	@Override
	public ProductDTO getProductById(Long id) {
		return productRepository.findById(id).map(this::convertToDTO).orElse(null);
	}

	@Override
	public List<ProductDTO> getProductsByCategory(String category) {
		return productRepository.findByCategory(category).stream().map(this::convertToDTO).collect(Collectors.toList());
	}

	@Override
	public List<ProductDTO> getFeaturedProducts() {
		return productRepository.findByIsFeaturedTrue().stream().map(this::convertToDTO).collect(Collectors.toList());
	}

	// =================================================================
	// 3. CÁC HÀM MỚI CHO ADMIN (THÊM, SỬA, XÓA)
	// =================================================================

	@Override
	@Transactional // Đảm bảo giao dịch database
	public ProductDTO createProduct(ProductDTO dto) {
		Product product = new Product();
		mapToEntity(product, dto); // Map dữ liệu từ FE vào Entity mới
		Product savedProduct = productRepository.save(product);
		return convertToDTO(savedProduct);
	}

	@Override
	@Transactional
	public ProductDTO updateProduct(Long id, ProductDTO dto) {
		Product existingProduct = productRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Không tìm thấy sản phẩm với ID: " + id));

		mapToEntity(existingProduct, dto); // Cập nhật dữ liệu mới vào Entity cũ

		Product updatedProduct = productRepository.save(existingProduct);
		return convertToDTO(updatedProduct);
	}

	@Override
	@Transactional
	public void deleteProduct(Long id) {
		if (!productRepository.existsById(id)) {
			throw new RuntimeException("Không tìm thấy sản phẩm để xóa!");
		}
		productRepository.deleteById(id);
	}
}
