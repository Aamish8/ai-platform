package com.aamish.ai_service.dtos;

import lombok.Builder;
import lombok.Data;
@Builder
@Data
public class AskRequest {
  private String question;
}
