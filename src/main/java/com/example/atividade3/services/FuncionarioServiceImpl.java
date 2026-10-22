package com.example.atividade3.services;

import com.example.atividade3.dtos.DadosFuncionarioDTO;
import com.example.atividade3.dtos.DadosProjetoDTO;
import com.example.atividade3.dtos.FuncionarioDTO;
import com.example.atividade3.exceptions.RegraNegocioException;
import com.example.atividade3.models.Funcionario;
import com.example.atividade3.models.Projeto;
import com.example.atividade3.models.Setor;
import com.example.atividade3.repositories.FuncionarioRepository;
import com.example.atividade3.repositories.SetorRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class FuncionarioServiceImpl implements FuncionarioService {

    private final FuncionarioRepository funcionarioRepository;
    private final SetorRepository setorRepository;

    public FuncionarioServiceImpl(FuncionarioRepository funcionarioRepository, SetorRepository setorRepository) {
        this.funcionarioRepository = funcionarioRepository;
        this.setorRepository = setorRepository;
    }

    @Override
    public void adicionar(FuncionarioDTO dto) {

        if (dto == null) {
            throw new RegraNegocioException(
                    "Os dados do funcionário são obrigatórios.");
        }

        if (dto.getNome() == null || dto.getNome().isBlank()) {
            throw new RegraNegocioException(
                    "O nome do funcionário é obrigatório.");
        }

        if (dto.getSetorId() == null) {
            throw new RegraNegocioException(
                    "O setor do funcionário é obrigatório.");
        }

        Setor setor = setorRepository.findById(dto.getSetorId())
                .orElseThrow(() ->
                        new RegraNegocioException(
                                "Setor não encontrado."));

        Funcionario funcionario = new Funcionario(dto.getNome());
        funcionario.setSetor(setor);

        funcionarioRepository.save(funcionario);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DadosProjetoDTO> buscarProjetos(Integer idFuncionario) {

        Funcionario funcionario = funcionarioRepository
                .buscarFuncionarioComProjetos(idFuncionario)
                .orElseThrow(() ->
                        new RegraNegocioException(
                                "Funcionário não encontrado."));

        List<DadosProjetoDTO> resultado = new ArrayList<>();

        for (Projeto projeto : funcionario.getProjetos()) {

            List<DadosFuncionarioDTO> funcionariosProjeto =
                    new ArrayList<>();

            for (Funcionario f : projeto.getFuncionarios()) {
                funcionariosProjeto.add(
                        new DadosFuncionarioDTO(
                                f.getId(),
                                f.getNome()
                        )
                );
            }

            DadosProjetoDTO dadosProjeto = new DadosProjetoDTO(
                    projeto.getId(),
                    projeto.getDescricao(),
                    projeto.getDataInicio(),
                    projeto.getDataFim(),
                    funcionariosProjeto
            );

            resultado.add(dadosProjeto);
        }

        return resultado;
    }
}