package com.futureskill.api.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InscricaoResponse {
    private Long id;
    private Long usuarioId;
    private String usuarioNome;
    private Long cursoId;
    private String cursoTitulo;
    private LocalDateTime dataInscricao;
}

