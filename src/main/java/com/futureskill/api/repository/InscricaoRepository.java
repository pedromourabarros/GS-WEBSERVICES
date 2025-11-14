package com.futureskill.api.repository;

import com.futureskill.api.model.Inscricao;
import com.futureskill.api.model.Usuario;
import com.futureskill.api.model.Curso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InscricaoRepository extends JpaRepository<Inscricao, Long> {
    List<Inscricao> findByUsuario(Usuario usuario);
    List<Inscricao> findByCurso(Curso curso);
    Optional<Inscricao> findByUsuarioAndCurso(Usuario usuario, Curso curso);
    boolean existsByUsuarioAndCurso(Usuario usuario, Curso curso);
}

