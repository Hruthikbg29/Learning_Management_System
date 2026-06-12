//package com.LMS_Project.ai.controller;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.LMS_Project.ai.dto.AiRequest;
//import com.LMS_Project.ai.dto.AiResponse;
//import com.LMS_Project.ai.service.AiService;
//import com.LMS_Project.dto.response.ApiResponse;
//
//import io.swagger.v3.oas.annotations.parameters.RequestBody;
//import jakarta.validation.Valid;
//
//@RestController
//@RequestMapping("/api/ai")
//@CrossOrigin(origins = "*")
//public class AiController {
//	private static final Logger logger =
//            LoggerFactory.getLogger(AiController.class);
//
//    private final AiService aiService;
//
//    public AiController(AiService aiService) {
//        this.aiService = aiService;
//    }
//
//    
//    // POST /api/ai/chat
//    @PostMapping("/chat")
//    public ResponseEntity<ApiResponse<AiResponse>> chat(
//            @Valid @RequestBody AiRequest request) {
//        logger.info("API request received: POST /api/ai/chat");
//        AiResponse response = aiService.chat(request);
//        return ResponseEntity.ok(
//                ApiResponse.success("AI response generated", response));
//    }
//
//    // POST /api/ai/course-recommend
//    @PostMapping("/course-recommend")
//    public ResponseEntity<ApiResponse<AiResponse>> recommendCourse(
//            @Valid @RequestBody AiRequest request) {
//        logger.info("API request received: POST /api/ai/course-recommend");
//        AiResponse response = aiService.recommendCourse(request);
//        return ResponseEntity.ok(
//                ApiResponse.success("Course recommendations generated", response));
//    }
//
//    // POST /api/ai/summarize
//    @PostMapping("/summarize")
//    public ResponseEntity<ApiResponse<AiResponse>> summarizeCourse(
//            @Valid @RequestBody AiRequest request) {
//        logger.info("API request received: POST /api/ai/summarize");
//        AiResponse response = aiService.summarizeCourse(request);
//        return ResponseEntity.ok(
//                ApiResponse.success("Course summarized successfully", response));
//    }
//}
