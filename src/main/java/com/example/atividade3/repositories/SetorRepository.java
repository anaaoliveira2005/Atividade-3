package com.example.atividade3.repositories;

import com.example.atividade3.models.Setor;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SetorRepository extends JpaRepository<Setor, Integer> {

    @Query("SELECT DISTINCT s FROM Setor s " + "LEFT JOIN FETCH s.funcionarios")
    List<Setor> buscarTodosComFuncionarios();
}