package com.example.atividade3.services;

import com.example.atividade3.dtos.SetorDTO;
import com.example.atividade3.dtos.DadosSetorDTO;

public interface SetorService {

    void adicionar(SetorDTO setor);

    DadosSetorDTO buscarSetorPorId(Integer idSetor);
}