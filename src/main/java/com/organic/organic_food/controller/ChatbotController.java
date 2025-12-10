package com.organic.organic_food.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.organic.organic_food.dto.ChatbotRequest;
import com.organic.organic_food.dto.ChatbotResponse;
import com.organic.organic_food.service.ChatbotService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/chatbot")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173") // Hoặc cấu hình CORS toàn cục
public class ChatbotController {

	private final ChatbotService chatbotService;

	/**
	 * Endpoint để người dùng gửi tin nhắn và nhận phản hồi từ chatbot.
	 * 
	 * @param request Đối tượng chứa tin nhắn của người dùng.
	 * @return Đối tượng chứa câu trả lời của bot.
	 */
	@PostMapping("/query")
	public ChatbotResponse handleQuery(@RequestBody ChatbotRequest request) {
		return chatbotService.processUserQuery(request);
	}
}