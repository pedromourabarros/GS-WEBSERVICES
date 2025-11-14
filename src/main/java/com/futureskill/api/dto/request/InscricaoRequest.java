package com.futureskill.api.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InscricaoRequest {
    
    @NotNull(message = "ID do curso é obrigatório")
    private Long cursoId;
}

