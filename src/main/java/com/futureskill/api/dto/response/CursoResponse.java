package com.futureskill.api.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CursoResponse {
    private Long id;
    private String titulo;
    private String descricao;
    private String categoria;
    private Integer cargaHoraria;
}

