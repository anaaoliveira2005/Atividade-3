package com.example.atividade3.services;

import com.example.atividade3.dtos.ProjetoDTO;
import com.example.atividade3.dtos.DadosProjetoDTO;

import java.time.LocalDate;
import java.util.List;

public interface ProjetoService {

    void adicionar(ProjetoDTO projeto);

    DadosProjetoDTO buscarProjetoPorId(Integer id);

    void vincularFuncionario(Integer idProjeto, Integer idFuncionario);

    List<DadosProjetoDTO> listarTodos();

    List<DadosProjetoDTO> buscarPorPeriodo(LocalDate dataInicio, LocalDate dataFim);
}