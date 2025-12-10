package com.organic.organic_food.service.impl;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.google.cloud.dialogflow.v2.QueryResult;
import com.google.protobuf.Value;
import com.organic.organic_food.dto.ChatbotRequest;
import com.organic.organic_food.dto.ChatbotResponse;
import com.organic.organic_food.dto.ProductDTO;
import com.organic.organic_food.gateway.DialogflowGateway;
import com.organic.organic_food.service.ChatbotService;
import com.organic.organic_food.service.ProductService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChatbotServiceImpl implements ChatbotService {

	private final DialogflowGateway dialogflowGateway;
	private final ProductService productService;

	@Override
	public ChatbotResponse processUserQuery(ChatbotRequest request) {
		try {
			String sessionId = UUID.randomUUID().toString();
			QueryResult dialogflowResult = dialogflowGateway.detectIntent(request.getMessage(), sessionId);

			String intentName = dialogflowResult.getIntent().getDisplayName();
			System.out.println(">>>>>> Dialogflow matched Intent: '" + intentName + "'");
			String responseText;

			switch (intentName) {
			case "SearchProduct":
				responseText = handleSearchProductIntent(dialogflowResult);
				break;

			case "GetFeaturedProducts":
				responseText = handleGetFeaturedProductsIntent();
				break;

			default:
				responseText = dialogflowResult.getFulfillmentText();
				break;
			}

			return new ChatbotResponse(responseText);

		} catch (Exception e) {
			e.printStackTrace();
			return new ChatbotResponse("Xin lỗi, tôi đang gặp sự cố nhỏ. Vui lòng thử lại sau.");
		}
	}

	/**
	 * ✅ ĐÃ CẬP NHẬT: Xử lý ý định tìm kiếm sản phẩm và tạo ra câu trả lời chứa link
	 * Markdown.
	 */
	private String handleSearchProductIntent(QueryResult dialogflowResult) {
		Map<String, Value> parameters = dialogflowResult.getParameters().getFieldsMap();
		if (!parameters.containsKey("product-name")) {
			return "Bạn muốn tìm sản phẩm nào ạ?";
		}

		String productName = parameters.get("product-name").getStringValue();
		if (productName.trim().isEmpty()) {
			return "Bạn muốn tìm sản phẩm nào ạ?";
		}

		List<ProductDTO> products = productService.searchProducts(productName);

		if (products.isEmpty()) {
			return "Rất tiếc, chúng tôi không tìm thấy sản phẩm nào có tên '" + productName + "'.";
		}
		// Trường hợp tìm thấy 1 sản phẩm
		else if (products.size() == 1) {
			ProductDTO p = products.get(0);
			// Cú pháp Markdown: [Văn bản hiển thị](slug)
			return String.format("Tìm thấy rồi! Đây là sản phẩm [%s](%s) với giá %.0fđ.", p.getName(), p.getSlug(), // Gửi
																													// slug
																													// về
																													// cho
																													// frontend
					p.getPrice());
		}
		// Trường hợp tìm thấy nhiều sản phẩm
		else {
			String productLinks = products.stream().limit(3) // Giới hạn 3 sản phẩm để câu trả lời không quá dài
					// Tạo link Markdown cho mỗi sản phẩm
					.map(p -> String.format("[%s](%s)", p.getName(), p.getSlug()))
					// Nối các link lại với nhau, mỗi link trên một dòng có gạch đầu dòng
					.collect(Collectors.joining("\n- "));
			return "Chúng tôi tìm thấy một vài sản phẩm:\n- " + productLinks;
		}
	}

	/**
	 * ✅ ĐÃ CẬP NHẬT: Xử lý ý định lấy sản phẩm nổi bật và tạo ra câu trả lời chứa
	 * link Markdown.
	 */
	private String handleGetFeaturedProductsIntent() {
		List<ProductDTO> featuredProducts = productService.getFeaturedProducts();
		if (featuredProducts.isEmpty()) {
			return "Hiện tại cửa hàng chưa có sản phẩm nào nổi bật ạ.";
		} else {
			// Tạo một chuỗi các link Markdown cho các sản phẩm nổi bật
			String productLinks = featuredProducts.stream().limit(3)
					.map(p -> String.format("[%s](%s)", p.getName(), p.getSlug())).collect(Collectors.joining("\n- "));
			return "Đây là các sản phẩm đang hot tại shop:\n- " + productLinks;
		}
	}
}