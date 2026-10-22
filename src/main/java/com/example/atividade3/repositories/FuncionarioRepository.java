package com.example.atividade3.repositories;

import com.example.atividade3.models.Funcionario;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface FuncionarioRepository extends JpaRepository<Funcionario, Integer> {

    @Query("SELECT DISTINCT f FROM Funcionario f " + "LEFT JOIN FETCH f.projetos " + "WHERE f.id = :id")
    Optional<Funcionario> buscarFuncionarioComProjetos(@Param("id") Integer id);
}