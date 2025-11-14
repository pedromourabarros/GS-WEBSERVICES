package com.futureskill.api.model.vo;

import lombok.Value;

@Value
public class Email {
    String valor;
    
    public Email(String valor) {
        if (valor == null || valor.isBlank() || !valor.contains("@")) {
            throw new IllegalArgumentException("Email inválido");
        }
        this.valor = valor.toLowerCase().trim();
    }
    
    @Override
    public String toString() {
        return valor;
    }
}

