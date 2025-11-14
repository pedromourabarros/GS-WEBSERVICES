package com.futureskill.api.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CursoRequest {
    
    @NotBlank(message = "Título é obrigatório")
    @Size(min = 3, max = 200, message = "Título deve ter entre 3 e 200 caracteres")
    private String titulo;
    
    @NotBlank(message = "Descrição é obrigatória")
    private String descricao;
    
    @NotBlank(message = "Categoria é obrigatória")
    @Size(max = 100, message = "Categoria deve ter no máximo 100 caracteres")
    private String categoria;
    
    @NotNull(message = "Carga horária é obrigatória")
    @Min(value = 1, message = "Carga horária deve ser maior que zero")
    private Integer cargaHoraria;
}

