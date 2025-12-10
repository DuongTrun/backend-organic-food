package com.organic.organic_food.service;

import com.organic.organic_food.dto.ChatbotRequest;
import com.organic.organic_food.dto.ChatbotResponse;

public interface ChatbotService {

	/**
	 * Xử lý truy vấn từ người dùng, giao tiếp với AI và logic nghiệp vụ để tạo ra
	 * câu trả lời.
	 * 
	 * @param request Yêu cầu từ người dùng.
	 * @return Phản hồi từ chatbot.
	 */
	ChatbotResponse processUserQuery(ChatbotRequest request);
}