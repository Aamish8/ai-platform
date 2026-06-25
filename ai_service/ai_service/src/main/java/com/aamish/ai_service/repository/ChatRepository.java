package com.aamish.ai_service.repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.aamish.ai_service.entity.ChatHistory;
@Repository
public interface ChatRepository extends JpaRepository<ChatHistory,Long>{
	 Page<ChatHistory> findByEmail(String email,Pageable pageable);
}
