package com.futureskill.api.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "cursos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Curso {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, length = 200)
    private String titulo;
    
    @Column(nullable = false, columnDefinition = "TEXT")
    private String descricao;
    
    @Column(nullable = false, length = 100)
    private String categoria;
    
    @Column(nullable = false)
    private int cargaHoraria;
}

