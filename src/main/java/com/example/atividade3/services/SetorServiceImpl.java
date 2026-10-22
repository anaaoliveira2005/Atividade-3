package com.example.atividade3.services;

import com.example.atividade3.dtos.DadosFuncionarioDTO;
import com.example.atividade3.dtos.DadosSetorDTO;
import com.example.atividade3.dtos.SetorDTO;
import com.example.atividade3.exceptions.RegraNegocioException;
import com.example.atividade3.models.Funcionario;
import com.example.atividade3.models.Setor;
import com.example.atividade3.repositories.SetorRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class SetorServiceImpl implements SetorService {

    private final SetorRepository setorRepository;

    public SetorServiceImpl(SetorRepository setorRepository) {
        this.setorRepository = setorRepository;
    }

    @Override
    public void adicionar(SetorDTO dto) {

        if (dto == null) {
            throw new RegraNegocioException(
                    "Os dados do setor são obrigatórios.");
        }

        if (dto.getNome() == null || dto.getNome().isBlank()) {
            throw new RegraNegocioException(
                    "O nome do setor é obrigatório.");
        }

        Setor setor = new Setor(dto.getNome());

        setorRepository.save(setor);
    }

    @Override
    @Transactional(readOnly = true)
    public DadosSetorDTO buscarSetorPorId(Integer idSetor) {

        Setor setor = setorRepository.findById(idSetor)
                .orElseThrow(() ->
                        new RegraNegocioException(
                                "Setor não encontrado."));

        List<DadosFuncionarioDTO> funcionarios = new ArrayList<>();

        for (Funcionario funcionario : setor.getFuncionarios()) {
            funcionarios.add(
                    new DadosFuncionarioDTO(
                            funcionario.getId(),
                            funcionario.getNome()
                    )
            );
        }

        return new DadosSetorDTO(
                setor.getId(),
                setor.getNome(),
                funcionarios
        );
    }
}