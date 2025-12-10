package com.organic.organic_food.gateway;

import java.io.IOException;

//import com.google.protobuf.Value;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.google.cloud.dialogflow.v2.DetectIntentResponse;
import com.google.cloud.dialogflow.v2.QueryInput;
import com.google.cloud.dialogflow.v2.QueryResult;
import com.google.cloud.dialogflow.v2.SessionName;
import com.google.cloud.dialogflow.v2.SessionsClient;
import com.google.cloud.dialogflow.v2.TextInput;

/**
 * Lớp Gateway chịu trách nhiệm giao tiếp trực tiếp với Google Dialogflow API.
 * Nó ẩn đi sự phức tạp của SDK khỏi lớp Service.
 */
@Component
public class DialogflowGateway {

	@Value("${gcp.project-id}")
	private String projectId;

	/**
	 * Gửi một đoạn văn bản đến Dialogflow và trả về kết quả đã được phân tích.
	 * 
	 * @param text      Tin nhắn của người dùng.
	 * @param sessionId Một mã định danh duy nhất cho cuộc trò chuyện.
	 * @return Kết quả từ Dialogflow.
	 * @throws IOException Nếu có lỗi khi khởi tạo SessionsClient.
	 */
	public QueryResult detectIntent(String text, String sessionId) throws IOException {
		// Sử dụng try-with-resources để tự động đóng client
		try (SessionsClient sessionsClient = SessionsClient.create()) {
			SessionName session = SessionName.of(projectId, sessionId);
			TextInput.Builder textInput = TextInput.newBuilder().setText(text).setLanguageCode("vi-VN");
			QueryInput queryInput = QueryInput.newBuilder().setText(textInput).build();

			System.out.println("Sending to Dialogflow: " + text);
			DetectIntentResponse response = sessionsClient.detectIntent(session, queryInput);

			return response.getQueryResult();
		}
	}
}