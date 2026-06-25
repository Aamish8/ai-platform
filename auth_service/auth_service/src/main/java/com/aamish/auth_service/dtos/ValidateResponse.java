package com.aamish.auth_service.dtos;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ValidateResponse {
    private String email;
    private boolean valid;
    
}
