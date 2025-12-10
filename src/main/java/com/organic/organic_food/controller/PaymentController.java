package com.organic.organic_food.controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.organic.organic_food.dto.PaymentDTO;
import com.organic.organic_food.service.PaymentService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/payment")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class PaymentController {

	private final PaymentService paymentService;

	// 1. API TẠO URL THANH TOÁN
	@PostMapping("/create_payment")
	public ResponseEntity<?> createPayment(@RequestBody PaymentDTO paymentDTO, HttpServletRequest request) {
		// (Tùy chọn) Lưu đơn hàng vào DB với trạng thái PENDING tại đây...

		String vnpayUrl = paymentService.createVNPayPayment(paymentDTO, request);
		return ResponseEntity.ok(vnpayUrl);
	}

	// 2. API XÁC THỰC KẾT QUẢ (MỚI THÊM)
	// Frontend sẽ gọi API này khi VNPay chuyển hướng về
	@GetMapping("/vnpay_return")
	public ResponseEntity<?> paymentReturn(HttpServletRequest request) {
		// Gọi service để kiểm tra chữ ký (checksum)
		int paymentStatus = paymentService.orderReturn(request);

		// Lấy các thông tin VNPay trả về để hiển thị
		String orderInfo = request.getParameter("vnp_OrderInfo");
		String paymentTime = request.getParameter("vnp_PayDate");
		String transactionId = request.getParameter("vnp_TransactionNo");
		String totalPrice = request.getParameter("vnp_Amount");

		Map<String, Object> result = Map.of("status", paymentStatus == 1 ? "SUCCESS" : "FAILED", "orderInfo",
				orderInfo == null ? "" : orderInfo, "paymentTime", paymentTime == null ? "" : paymentTime,
				"transactionId", transactionId == null ? "" : transactionId, "totalPrice",
				totalPrice == null ? "" : totalPrice);

		if (paymentStatus == 1) {
			// TODO: LOGIC QUAN TRỌNG
			// Tại đây bạn nên gọi OrderService để update trạng thái đơn hàng trong DB thành
			// "PAID"
			// Ví dụ: orderService.updateStatus(orderId, "PAID");

			return ResponseEntity.ok(result);
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
		}
	}
}