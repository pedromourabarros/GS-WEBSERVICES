package com.futureskill.api.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse {
    private String token;
    private String tipo;
    private Long usuarioId;
    private String email;
    private String role;
}

