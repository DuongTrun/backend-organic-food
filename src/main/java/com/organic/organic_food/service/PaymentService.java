//package com.organic.organic_food.service;
//
//import java.net.URLEncoder;
//import java.nio.charset.StandardCharsets;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Calendar;
//import java.util.Collections;
//import java.util.Enumeration;
//import java.util.HashMap;
//import java.util.Iterator;
//import java.util.List;
//import java.util.Map;
//import java.util.TimeZone;
//
//import org.springframework.stereotype.Service;
//
//import com.organic.organic_food.config.VNPayConfig;
//import com.organic.organic_food.dto.PaymentDTO;
//
//import jakarta.servlet.http.HttpServletRequest;
//
//@Service
//public class PaymentService {
//
//	// 1. TẠO URL THANH TOÁN (Logic thật)
////	public String createVNPayPayment(PaymentDTO paymentDTO, HttpServletRequest request) {
////		long amount = paymentDTO.getAmount() * 100; // VNPay yêu cầu nhân 100
////
////		Map<String, String> vnp_Params = new HashMap<>();
////		vnp_Params.put("vnp_Version", "2.1.0");
////		vnp_Params.put("vnp_Command", "pay");
////		vnp_Params.put("vnp_TmnCode", VNPayConfig.vnp_TmnCode);
////		vnp_Params.put("vnp_Amount", String.valueOf(amount));
////		vnp_Params.put("vnp_CurrCode", "VND");
////		vnp_Params.put("vnp_BankCode", "NCB"); // Nếu muốn động, có thể lấy từ DTO
////
////		// Random mã giao dịch (Thực tế nên dùng Order ID của bạn)
////		vnp_Params.put("vnp_TxnRef", VNPayConfig.getRandomNumber(8));
////
////		// Lấy nội dung thanh toán thực tế, xử lý null
////		String orderInfo = (paymentDTO.getOrderInfo() != null && !paymentDTO.getOrderInfo().isEmpty())
////				? paymentDTO.getOrderInfo()
////				: "Thanh toan don hang";
////		vnp_Params.put("vnp_OrderInfo", orderInfo);
////
////		vnp_Params.put("vnp_OrderType", "other");
////		vnp_Params.put("vnp_Locale", "vn");
////		vnp_Params.put("vnp_ReturnUrl", VNPayConfig.vnp_ReturnUrl);
////
////		// Lấy IP thật của khách hàng
//////		vnp_Params.put("vnp_IpAddr", VNPayConfig.getIpAddress(request));
////		vnp_Params.put("vnp_IpAddr", "127.0.0.1");
////		// Thời gian tạo & Hết hạn
////		Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
////		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
////		String vnp_CreateDate = formatter.format(cld.getTime());
////		vnp_Params.put("vnp_CreateDate", vnp_CreateDate);
////
////		cld.add(Calendar.MINUTE, 15);
////		String vnp_ExpireDate = formatter.format(cld.getTime());
////		vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);
////
////		// Build URL & Mã hóa
////		List<String> fieldNames = new ArrayList<>(vnp_Params.keySet());
////		Collections.sort(fieldNames);
////		StringBuilder hashData = new StringBuilder();
////		StringBuilder query = new StringBuilder();
////		Iterator<String> itr = fieldNames.iterator();
////
////		while (itr.hasNext()) {
////			String fieldName = itr.next();
////			String fieldValue = vnp_Params.get(fieldName);
////			if ((fieldValue != null) && (fieldValue.length() > 0)) {
////				try {
////					// Build hash data
////					hashData.append(fieldName);
////					hashData.append('=');
////					// CHUẨN HÓA: Dùng UTF-8 để hỗ trợ tiếng Việt và ký tự đặc biệt
////					hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.UTF_8.toString()));
////
////					// Build query
////					query.append(URLEncoder.encode(fieldName, StandardCharsets.UTF_8.toString()));
////					query.append('=');
////					query.append(URLEncoder.encode(fieldValue, StandardCharsets.UTF_8.toString()));
////
////					if (itr.hasNext()) {
////						query.append('&');
////						hashData.append('&');
////					}
////				} catch (Exception e) {
////					e.printStackTrace();
////				}
////			}
////		}
////
////		String queryUrl = query.toString();
////		// Tạo chữ ký bảo mật
////		String vnp_SecureHash = VNPayConfig.hmacSHA512(VNPayConfig.secretKey, hashData.toString());
////		queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
////
////		// =================================================================
////		// 🔥 LOG DEBUG (IN RA CONSOLE ĐỂ SOI LỖI)
////		// =================================================================
////		System.out.println("-------------------------------------------------");
////		System.out.println("✅ VNPAY DEBUG START");
////		System.out.println("1. TmnCode:    [" + VNPayConfig.vnp_TmnCode + "]");
////		System.out.println("2. SecretKey:  [" + VNPayConfig.secretKey + "] (Kiểm tra kỹ xem có dấu cách cuối không!)");
////		System.out.println("3. HashData (Chuỗi trước khi mã hóa):");
////		System.out.println("[" + hashData.toString() + "]");
////		System.out.println("4. SecureHash (Chữ ký tạo ra): " + vnp_SecureHash);
////		System.out.println("5. Final URL: " + VNPayConfig.vnp_PayUrl + "?" + queryUrl);
////		System.out.println("-------------------------------------------------");
////
////		return VNPayConfig.vnp_PayUrl + "?" + queryUrl;
////	}
//	public String createVNPayPayment(PaymentDTO paymentDTO, HttpServletRequest request) {
//		long amount = paymentDTO.getAmount() * 100;
//
//		Map<String, String> vnp_Params = new HashMap<>();
//		vnp_Params.put("vnp_Version", "2.1.0");
//		vnp_Params.put("vnp_Command", "pay");
//		vnp_Params.put("vnp_TmnCode", VNPayConfig.vnp_TmnCode);
//		vnp_Params.put("vnp_Amount", String.valueOf(amount));
//		vnp_Params.put("vnp_CurrCode", "VND");
//		vnp_Params.put("vnp_BankCode", "NCB");
//		vnp_Params.put("vnp_TxnRef", VNPayConfig.getRandomNumber(8));
//
//		// Fix nội dung không dấu để test cho chắc
//		vnp_Params.put("vnp_OrderInfo", "Thanh toan don hang");
//
//		vnp_Params.put("vnp_OrderType", "other");
//		vnp_Params.put("vnp_Locale", "vn");
//		vnp_Params.put("vnp_ReturnUrl", VNPayConfig.vnp_ReturnUrl);
//
//		// 🔥 QUAN TRỌNG: Fix cứng IP để tránh lỗi IPv6
//		vnp_Params.put("vnp_IpAddr", "127.0.0.1");
//
//		Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
//		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
//		String vnp_CreateDate = formatter.format(cld.getTime());
//		vnp_Params.put("vnp_CreateDate", vnp_CreateDate);
//
//		cld.add(Calendar.MINUTE, 15);
//		String vnp_ExpireDate = formatter.format(cld.getTime());
//		vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);
//
//		List<String> fieldNames = new ArrayList<>(vnp_Params.keySet());
//		Collections.sort(fieldNames);
//		StringBuilder hashData = new StringBuilder();
//		StringBuilder query = new StringBuilder();
//		Iterator<String> itr = fieldNames.iterator();
//
//		while (itr.hasNext()) {
//			String fieldName = itr.next();
//			String fieldValue = vnp_Params.get(fieldName);
//			if ((fieldValue != null) && (fieldValue.length() > 0)) {
//				try {
//					// Build hash data
//					hashData.append(fieldName);
//					hashData.append('=');
//					// 🔥 Dùng US_ASCII đúng chuẩn Sandbox
//					hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
//
//					// Build query
//					query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString()));
//					query.append('=');
//					query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
//
//					if (itr.hasNext()) {
//						query.append('&');
//						hashData.append('&');
//					}
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		}
//
//		String queryUrl = query.toString();
//		String vnp_SecureHash = VNPayConfig.hmacSHA512(VNPayConfig.secretKey, hashData.toString());
//		queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
//
//		// Log ra để kiểm tra
//		System.out.println("DEBUG IP: " + vnp_Params.get("vnp_IpAddr"));
//		System.out.println("Final URL: " + VNPayConfig.vnp_PayUrl + "?" + queryUrl);
//
//		return VNPayConfig.vnp_PayUrl + "?" + queryUrl;
//	}
//
//	// 2. XỬ LÝ KẾT QUẢ TRẢ VỀ (Quan trọng: Xác thực chữ ký)
//	public int orderReturn(HttpServletRequest request) {
//		Map<String, String> fields = new HashMap<>();
//		for (Enumeration<String> params = request.getParameterNames(); params.hasMoreElements();) {
//			String fieldName = null;
//			String fieldValue = null;
//			try {
//				fieldName = URLEncoder.encode(params.nextElement(), StandardCharsets.US_ASCII.toString());
//				fieldValue = URLEncoder.encode(request.getParameter(fieldName), StandardCharsets.US_ASCII.toString());
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//			if ((fieldValue != null) && (fieldValue.length() > 0)) {
//				fields.put(fieldName, fieldValue);
//			}
//		}
//
//		String vnp_SecureHash = request.getParameter("vnp_SecureHash");
//		if (fields.containsKey("vnp_SecureHashType")) {
//			fields.remove("vnp_SecureHashType");
//		}
//		if (fields.containsKey("vnp_SecureHash")) {
//			fields.remove("vnp_SecureHash");
//		}
//
//		// Tính lại chữ ký từ dữ liệu nhận được để so sánh
//		String signValue = VNPayConfig.hashAllFields(fields);
//
//		if (signValue.equals(vnp_SecureHash)) {
//			// Chữ ký đúng -> Kiểm tra trạng thái giao dịch
//			if ("00".equals(request.getParameter("vnp_ResponseCode"))) {
//				return 1; // Giao dịch thành công
//			} else {
//				return 0; // Giao dịch thất bại / Hủy
//			}
//		} else {
//			return -1; // Chữ ký không hợp lệ (Có dấu hiệu giả mạo)
//		}
//	}
//}

package com.organic.organic_food.service;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.stereotype.Service;

import com.organic.organic_food.dto.PaymentDTO;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class PaymentService {

	// HÀM TẠO URL - PHIÊN BẢN KHÔNG THỂ SAI
	public String createVNPayPayment(PaymentDTO paymentDTO, HttpServletRequest request) {

		// 1. CẤU HÌNH CỨNG (Để test xem có thông được không đã)
		String vnp_Version = "2.1.0";
		String vnp_Command = "pay";
		String vnp_TmnCode = "CGXZLS0Z"; // Mã Website chuẩn
		String vnp_HashSecret = "XNBGIBAINSFLKVROQPMTMOKXTJIAYEWP"; // Secret Key chuẩn
		String vnp_Url = "https://sandbox.vnpayment.vn/paymentv2/vpcpay.html";

		// 2. DỮ LIỆU CỐ ĐỊNH (Tránh lỗi do dữ liệu động)
		long amount = 10000000; // 100,000 VND
		String vnp_TxnRef = String.valueOf(System.currentTimeMillis()); // Mã đơn hàng theo thời gian
		String vnp_IpAddr = "127.0.0.1"; // IP V4 cố định
		String vnp_OrderInfo = "ThanhToanTest"; // Không dấu, không cách

		Map<String, String> vnp_Params = new HashMap<>();
		vnp_Params.put("vnp_Version", vnp_Version);
		vnp_Params.put("vnp_Command", vnp_Command);
		vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
		vnp_Params.put("vnp_Amount", String.valueOf(amount));
		vnp_Params.put("vnp_CurrCode", "VND");
		vnp_Params.put("vnp_BankCode", "NCB");
		vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
		vnp_Params.put("vnp_OrderInfo", vnp_OrderInfo);
		vnp_Params.put("vnp_OrderType", "other");
		vnp_Params.put("vnp_Locale", "vn");
		vnp_Params.put("vnp_ReturnUrl", "http://localhost:5173/payment-result");
		vnp_Params.put("vnp_IpAddr", vnp_IpAddr);

		// Thời gian
		Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
		String vnp_CreateDate = formatter.format(cld.getTime());
		vnp_Params.put("vnp_CreateDate", vnp_CreateDate);

		cld.add(Calendar.MINUTE, 15);
		String vnp_ExpireDate = formatter.format(cld.getTime());
		vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);

		// 3. SẮP XẾP VÀ HASH (Logic chuẩn của VNPay)
		List<String> fieldNames = new ArrayList<>(vnp_Params.keySet());
		Collections.sort(fieldNames);
		StringBuilder hashData = new StringBuilder();
		StringBuilder query = new StringBuilder();
		Iterator<String> itr = fieldNames.iterator();

		while (itr.hasNext()) {
			String fieldName = itr.next();
			String fieldValue = vnp_Params.get(fieldName);
			if ((fieldValue != null) && (fieldValue.length() > 0)) {
				try {
					// Build Hash Data
					hashData.append(fieldName);
					hashData.append('=');
					// Encode từng phần tử
					hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));

					// Build Query URL
					query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString()));
					query.append('=');
					query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));

					if (itr.hasNext()) {
						query.append('&');
						hashData.append('&');
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

		// 4. TẠO CHỮ KÝ (Dùng hàm nội bộ để đảm bảo không phụ thuộc file Config)
		String queryUrl = query.toString();
		String vnp_SecureHash = hmacSHA512(vnp_HashSecret, hashData.toString());
		queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;

		String paymentUrl = vnp_Url + "?" + queryUrl;

		// IN RA CONSOLE ĐỂ BẠN CLICK THỬ
		System.out.println("-------------------------------------------");
		System.out.println("👉 LINK THANH TOÁN: " + paymentUrl);
		System.out.println("-------------------------------------------");

		return paymentUrl;
	}

	// Hàm Hash nội bộ (Copy từ VNPayConfig sang đây để tránh lỗi config)
	private String hmacSHA512(final String key, final String data) {
		try {
			if (key == null || data == null)
				throw new NullPointerException();
			final Mac hmac512 = Mac.getInstance("HmacSHA512");
			byte[] hmacKeyBytes = key.getBytes();
			final SecretKeySpec secretKey = new SecretKeySpec(hmacKeyBytes, "HmacSHA512");
			hmac512.init(secretKey);
			byte[] dataBytes = data.getBytes(StandardCharsets.UTF_8);
			byte[] result = hmac512.doFinal(dataBytes);
			StringBuilder sb = new StringBuilder(2 * result.length);
			for (byte b : result) {
				sb.append(String.format("%02x", b & 0xff));
			}
			return sb.toString();
		} catch (Exception ex) {
			return "";
		}
	}

	// Hàm return tạm thời
	public int orderReturn(HttpServletRequest request) {
		return 1;
	}
}