package com.aamish.ai_service.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.client.RestTemplate;

import com.aamish.ai_service.component.AuthValidationFilter;

import lombok.RequiredArgsConstructor;
@RequiredArgsConstructor
@Configuration
public class AiConfiguration {
	private final AuthValidationFilter authFilter;
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		System.out.println("SecurityConfig Loaded");
		return http.csrf(csrf->csrf.disable())
				.authorizeHttpRequests(auth->auth.anyRequest().authenticated())
				.addFilterBefore(authFilter,UsernamePasswordAuthenticationFilter.class)
				.build();
	}
   @Bean
   public ChatClient chatClient(ChatModel model) {
	   return ChatClient.create(model);
   }
}
