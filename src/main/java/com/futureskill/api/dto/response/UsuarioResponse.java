package com.futureskill.api.dto.response;

import com.futureskill.api.model.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioResponse {
    private Long id;
    private String nome;
    private String email;
    private Role role;
}

