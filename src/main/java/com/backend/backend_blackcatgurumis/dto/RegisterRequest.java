package com.backend.backend_blackcatgurumis.dto;

import lombok.Data;

@Data
public class RegisterRequest {
    private String nombre;
    private String email;
    private String password;
    private String telefono;
    private String rut;
}
