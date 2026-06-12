//package com.LMS_Project.ai.service;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import com.LMS_Project.ai.dto.AiRequest;
//import com.LMS_Project.ai.dto.AiResponse;
//
//import org.springframework.ai.chat.client.ChatClient;
//
//public class AiService {
//
//	 private static final Logger logger =
//	            LoggerFactory.getLogger(AiService.class);
//
//	    private final ChatClient chatClient;
//
//	    // Spring AI auto-creates ChatClient.Builder bean
//	    public AiService(ChatClient.Builder chatClientBuilder) {
//	        this.chatClient = chatClientBuilder.build();
//	    }
//	    
//	    // ── Feature 1: General Chat ───────────────────────────────────────────
//	    public AiResponse chat(AiRequest request) {
//	        logger.info("AI chat request: {}", request.getMessage());
//	        long start = System.currentTimeMillis();
//
//	        String response = chatClient.prompt()
//	                .system("You are a helpful assistant for a Learning Management System (LMS). " +
//	                        "Help students and instructors with course-related questions.")
//	                .user(request.getMessage())
//	                .call()
//	                .content();
//
//	        long time = System.currentTimeMillis() - start;
//	        logger.info("AI chat response received in {}ms", time);
//
//	        return AiResponse.builder()
//	                .response(response)
//	                .model("gpt-4o-mini")
//	                .processingTimeMs(time)
//	                .build();
//	    }
//
//	    // ── Feature 2: Course Recommender ────────────────────────────────────
//	    public AiResponse recommendCourse(AiRequest request) {
//	        logger.info("AI course recommendation request: {}", request.getMessage());
//	        long start = System.currentTimeMillis();
//
//	        String prompt = String.format(
//	            "You are a course advisor for an LMS system. " +
//	            "Based on the student's interest: '%s', " +
//	            "recommend 3 courses with brief explanations of why each course suits them. " +
//	            "Format your response clearly with course names and reasons.",
//	            request.getMessage()
//	        );
//
//	        String response = chatClient.prompt()
//	                .user(prompt)
//	                .call()
//	                .content();
//
//	        long time = System.currentTimeMillis() - start;
//
//	        return AiResponse.builder()
//	                .response(response)
//	                .model("gpt-4o-mini")
//	                .processingTimeMs(time)
//	                .build();
//	    }
//
//	    // ── Feature 3: Course Summarizer ──────────────────────────────────────
//	    public AiResponse summarizeCourse(AiRequest request) {
//	        logger.info("AI summarize request for: {}", request.getMessage());
//	        long start = System.currentTimeMillis();
//
//	        String prompt = String.format(
//	            "Summarize the following course description in 3 clear bullet points " +
//	            "that a student can quickly understand. Keep it simple and engaging:\n\n%s",
//	            request.getMessage()
//	        );
//
//	        String response = chatClient.prompt()
//	                .user(prompt)
//	                .call()
//	                .content();
//
//	        long time = System.currentTimeMillis() - start;
//
//	        return AiResponse.builder()
//	                .response(response)
//	                .model("gpt-4o-mini")
//	                .processingTimeMs(time)
//	                .build();
//	    }
//}
