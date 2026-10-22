package com.example.atividade3.services;

import com.example.atividade3.dtos.FuncionarioDTO;
import com.example.atividade3.dtos.DadosProjetoDTO;

import java.util.List;

public interface FuncionarioService {

    void adicionar(FuncionarioDTO funcionario);

    List<DadosProjetoDTO> buscarProjetos(Integer idFuncionario);
}