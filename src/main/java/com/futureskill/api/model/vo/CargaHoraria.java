package com.futureskill.api.model.vo;

import lombok.Value;

@Value
public class CargaHoraria {
    int horas;
    
    public CargaHoraria(int horas) {
        if (horas <= 0) {
            throw new IllegalArgumentException("Carga horária deve ser maior que zero");
        }
        this.horas = horas;
    }
    
    public int getHoras() {
        return horas;
    }
    
    @Override
    public String toString() {
        return String.valueOf(horas);
    }
}

