package com.aamish.auth_service.model;

import lombok.Data;

@Data
public class LoginRequest {
   private String email;
   private String password;
}
