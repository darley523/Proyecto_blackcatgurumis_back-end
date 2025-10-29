package com.backend.backend_blackcatgurumis.dto;

import lombok.Data;

@Data
public class LoginRequest {
    private String email;
    private String password;

}
