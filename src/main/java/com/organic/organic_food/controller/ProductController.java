//package com.organic.organic_food.controller;
//

//import java.util.List;
//
//import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.organic.organic_food.dto.ProductDTO;
//import com.organic.organic_food.service.ProductService;
//
//@RestController
//@RequestMapping("/api/products")
//@CrossOrigin(origins = "http://localhost:5173") // Cho phép frontend React gọi API
//public class ProductController {
//
//	private final ProductService productService;
//
//	public ProductController(ProductService productService) {
//		this.productService = productService;
//	}
//
//	// 👉 API tìm kiếm sản phẩm
//	@GetMapping("/search")
//	public List<ProductDTO> searchProducts(@RequestParam(required = false) String keyword) {
//		return productService.searchProducts(keyword);
//	}
//
//	// 👉 API lấy toàn bộ sản phẩm
//	@GetMapping
//	public List<ProductDTO> getAllProducts() {
//		return productService.getAllProducts();
//	}
//
//	// 👉 API lấy sản phẩm theo ID
//	@GetMapping("/{id}")
//	public ProductDTO getProductById(@PathVariable Long id) {
//		return productService.getProductById(id);
//	}
//}
//
//import java.util.List;
//
//import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.organic.organic_food.dto.ProductDTO;
//import com.organic.organic_food.service.ProductService;
//
//import lombok.RequiredArgsConstructor;
//
//@RestController
//@RequestMapping("/api/products")
//@RequiredArgsConstructor
//@CrossOrigin(origins = "http://localhost:5173")
//public class ProductController {
//
//	private final ProductService productService;
//
//	@GetMapping
//	public List<ProductDTO> getAll() {
//		return productService.getAllProducts();
//	}
//
//	@GetMapping("/{id}")
//	public ProductDTO getById(@PathVariable Long id) {
//		return productService.getProductById(id);
//	}
//
//	@GetMapping("/search")
//	public List<ProductDTO> search(@RequestParam String keyword) {
//		return productService.searchProducts(keyword);
//	}
//
//	@GetMapping("/category/{category}")
//	public List<ProductDTO> getByCategory(@PathVariable String category) {
//		return productService.getProductsByCategory(category);
//	}
//
//	@GetMapping("/featured")
//	public List<ProductDTO> getFeatured() {
//		return productService.getFeaturedProducts();
//	}
//}
package com.organic.organic_food.controller;

import java.util.List;

// Import sao (*) để lấy hết PostMapping, PutMapping, DeleteMapping...
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.organic.organic_food.dto.ProductDTO;
import com.organic.organic_food.service.ProductService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor

public class ProductController {

	private final ProductService productService;

	// ==========================================================
	// PHẦN 1: CÁC API CŨ (GIỮ NGUYÊN KHÔNG ĐỔI)
	// ==========================================================

	// URL ví dụ: /api/products?brands=ThienNhien&minPrice=50000&status=fresh
	@GetMapping
	public List<ProductDTO> getProducts(@RequestParam(required = false) List<String> brands,
			@RequestParam(required = false) List<String> categories, @RequestParam(required = false) Double minPrice,
			@RequestParam(required = false) Double maxPrice, @RequestParam(required = false) String origin,
			@RequestParam(required = false) Boolean isOrganic, @RequestParam(required = false) List<String> status) {
		return productService.getFilteredProducts(brands, categories, minPrice, maxPrice, origin, isOrganic, status);
	}

	@GetMapping("/{id}")
	public ProductDTO getById(@PathVariable Long id) {
		return productService.getProductById(id);
	}

	@GetMapping("/search")
	public List<ProductDTO> search(@RequestParam String keyword) {
		return productService.searchProducts(keyword);
	}

	@GetMapping("/category/{category}")
	public List<ProductDTO> getByCategory(@PathVariable String category) {
		return productService.getProductsByCategory(category);
	}

	@GetMapping("/featured")
	public List<ProductDTO> getFeatured() {
		return productService.getFeaturedProducts();
	}

	// ==========================================================
	// PHẦN 2: CÁC API MỚI DÀNH CHO ADMIN (THÊM, SỬA, XÓA)
	// ==========================================================

	// 1. Thêm sản phẩm mới
	@PostMapping
	public ProductDTO create(@RequestBody ProductDTO dto) {
		return productService.createProduct(dto);
	}

	// 2. Cập nhật sản phẩm
	@PutMapping("/{id}")
	public ProductDTO update(@PathVariable Long id, @RequestBody ProductDTO dto) {
		return productService.updateProduct(id, dto);
	}

	// 3. Xóa sản phẩm
	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id) {
		productService.deleteProduct(id);
	}
}

