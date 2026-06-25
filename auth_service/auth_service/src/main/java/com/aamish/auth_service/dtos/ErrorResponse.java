package com.aamish.auth_service.dtos;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ErrorResponse {
   private String message;
}
