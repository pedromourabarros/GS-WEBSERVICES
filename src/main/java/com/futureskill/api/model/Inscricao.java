package com.futureskill.api.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "inscricoes")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Inscricao {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "curso_id", nullable = false)
    private Curso curso;
    
    @Column(nullable = false)
    private LocalDateTime dataInscricao;
    
    @PrePersist
    protected void onCreate() {
        dataInscricao = LocalDateTime.now();
    }
}

