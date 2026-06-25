package com.aamish.ai_service.dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ValidateResponse {
    private String email;
    private boolean valid;
}
