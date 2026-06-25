package com.aamish.ai_service.aiService;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.aamish.ai_service.dtos.AskRequest;
import com.aamish.ai_service.dtos.AskResponse;
import com.aamish.ai_service.dtos.ChatResponse;
import com.aamish.ai_service.dtos.ValidateRequest;
import com.aamish.ai_service.dtos.ValidateResponse;
import com.aamish.ai_service.entity.ChatHistory;
import com.aamish.ai_service.repository.ChatRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AiService {
	private final ChatClient chatClient;
	private final StringRedisTemplate redisTemp;
	private final RestTemplate restTemplate;
	private final ChatRepository repo;
	public ValidateResponse response(String token) {
		ValidateRequest request = new ValidateRequest();
		request.setToken(token);
		return restTemplate.postForObject("http://localhost:8080/api/auth/validate", request,ValidateResponse.class);
	}
   public AskResponse askQuestion(AskRequest request) {
	   String email=SecurityContextHolder.getContext().getAuthentication().getName();
		String answer=redisTemp.opsForValue().get(request.getQuestion());
		if(answer!=null) {
			return AskResponse.builder().request(answer).build();
		}
	   String str=chatClient.prompt(request.getQuestion()).call().content();
	   redisTemp.opsForValue()
       .set(request.getQuestion(), str);
	    ChatHistory history=ChatHistory.builder()
	    		.email(email)
	    		.question(request.getQuestion())
	    		.answer(str)
	    		.createdAt(LocalDateTime.now())
	    		.build();
	    repo.save(history);
	   return AskResponse.builder().request(str).build();
   }
public Page<ChatResponse> getChats(int page,int size) {
	String email=SecurityContextHolder.getContext().getAuthentication().getName();
	Pageable pageable=PageRequest.of(page,size);
	Page<ChatHistory> pages = repo.findByEmail(email, pageable);

	return pages.map(chat ->
    ChatResponse.builder()
        .question(chat.getQuestion())
        .answer(chat.getAnswer())
        .createdAt(chat.getCreatedAt())
        .build()
);
}
   
}
