package com.aamish.ai_service.controller;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.aamish.ai_service.aiService.AiService;
import com.aamish.ai_service.dtos.AskRequest;
import com.aamish.ai_service.dtos.AskResponse;
import com.aamish.ai_service.dtos.ChatResponse;
import com.aamish.ai_service.dtos.ValidateRequest;
import com.aamish.ai_service.dtos.ValidateResponse;
import com.aamish.ai_service.entity.ChatHistory;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/ai")
public class AiController {
   private final AiService service;
   @PostMapping("/ask")
   public AskResponse askQuestion(@RequestBody AskRequest request) {
	   return service.askQuestion(request);  
   }
   @PostMapping("/test")
   public ValidateResponse test(@RequestBody ValidateRequest request) {
	  return service.response(request.getToken());
   }
   @GetMapping("/history")
   public Page<ChatResponse> getChats(@RequestParam(defaultValue="1")int page,
		   @RequestParam(defaultValue="1")int size){
	   return service.getChats(page,size);
   }
}
