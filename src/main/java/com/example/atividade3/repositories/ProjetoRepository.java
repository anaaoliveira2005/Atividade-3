package com.example.atividade3.repositories;

import com.example.atividade3.models.Projeto;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProjetoRepository extends JpaRepository<Projeto, Integer> {

    @Query("SELECT DISTINCT p FROM Projeto p " + "LEFT JOIN FETCH p.funcionarios " + "WHERE p.id = :id")
    Optional<Projeto> buscarProjetoComFuncionarios(@Param("id") Integer id);

    @Query("SELECT p FROM Projeto p " + "WHERE p.dataInicio BETWEEN :dataInicio AND :dataFim")
    List<Projeto> buscarPorPeriodo(@Param("dataInicio") LocalDate dataInicio, @Param("dataFim") LocalDate dataFim);
}