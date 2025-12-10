//package com.organic.organic_food.entity;
//
//import jakarta.persistence.Entity;
//import jakarta.persistence.GeneratedValue;
//import jakarta.persistence.GenerationType;
//import jakarta.persistence.Id;
//import jakarta.persistence.Table;
//import lombok.AllArgsConstructor;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//
//
//@Entity
//@Table(name = "products")
//@Getter
//@Setter
//@NoArgsConstructor
//@AllArgsConstructor
//public class Product {
//	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	private Long id;
//
//	private String name;
//	private Double price;
//	private String image;
//	private String description;
//	private String category;
//	private boolean isFeatured;
//}

//package com.organic.organic_food.entity;
//
//import jakarta.persistence.Column;
//import jakarta.persistence.Entity;
//import jakarta.persistence.GeneratedValue;
//import jakarta.persistence.GenerationType;
//import jakarta.persistence.Id;
//import jakarta.persistence.Table;
//import lombok.AllArgsConstructor;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//
//@Entity
//@Table(name = "products")
//@Getter
//@Setter
//@NoArgsConstructor
//@AllArgsConstructor
//public class Product {
//	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	private Long id;
//
//	private String name;
//	private Double price;
//	private String image;
//	private String description;
//	private String category;
//
//	// ✅ CÁC TRƯỜNG MỚI ĐƯỢC THÊM VÀO
//	@Column(name = "origin")
//	private String origin;
//
//	@Column(name = "is_organic")
//	private boolean isOrganic;
//
//	@Column(name = "processing_status")
//	private String processingStatus;
//
//	@Column(name = "is_featured")
//	private boolean isFeatured;
//}

package com.organic.organic_food.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "products")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Product {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String name;

	private Double price; // Nếu muốn chính xác tuyệt đối về tiền tệ thì nên dùng BigDecimal, nhưng Double
							// vẫn ổn.

	private String slug; // Hibernate sẽ tự thêm cột này vào DB nếu chưa có

	private String image;

	@Column(columnDefinition = "TEXT")
	private String description;

	private String category;

	private String brand;

	// ✅ CÁC TRƯỜNG LỌC NÂNG CAO
	@Column(name = "origin")
	private String origin;

	@Column(name = "is_organic")
	private boolean isOrganic;

	@Column(name = "processing_status")
	private String processingStatus;

	@Column(name = "is_featured")
	private boolean isFeatured;
}