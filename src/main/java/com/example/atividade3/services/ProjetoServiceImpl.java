package com.example.atividade3.services;

import com.example.atividade3.dtos.DadosFuncionarioDTO;
import com.example.atividade3.dtos.DadosProjetoDTO;
import com.example.atividade3.dtos.ProjetoDTO;
import com.example.atividade3.exceptions.RegraNegocioException;
import com.example.atividade3.models.Funcionario;
import com.example.atividade3.models.Projeto;
import com.example.atividade3.repositories.FuncionarioRepository;
import com.example.atividade3.repositories.ProjetoRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class ProjetoServiceImpl implements ProjetoService {

    private final ProjetoRepository projetoRepository;
    private final FuncionarioRepository funcionarioRepository;

    public ProjetoServiceImpl(ProjetoRepository projetoRepository, FuncionarioRepository funcionarioRepository) {
        this.projetoRepository = projetoRepository;
        this.funcionarioRepository = funcionarioRepository;
    }

    @Override
    public void adicionar(ProjetoDTO dto) {

        if (dto == null) {
            throw new RegraNegocioException(
                    "Os dados do projeto são obrigatórios.");
        }

        if (dto.getDescricao() == null || dto.getDescricao().isBlank()) {
            throw new RegraNegocioException(
                    "A descrição do projeto é obrigatória.");
        }

        if (dto.getDataInicio() == null || dto.getDataFim() == null) {
            throw new RegraNegocioException(
                    "As datas de início e fim são obrigatórias.");
        }

        if (dto.getDataFim().isBefore(dto.getDataInicio())) {
            throw new RegraNegocioException(
                    "A data final não pode ser anterior à data inicial.");
        }

        Projeto projeto = new Projeto(
                dto.getDescricao(),
                dto.getDataInicio(),
                dto.getDataFim());

        projetoRepository.save(projeto);
    }

    @Override
    @Transactional(readOnly = true)
    public DadosProjetoDTO buscarProjetoPorId(Integer id) {

        Projeto projeto = projetoRepository
                .buscarProjetoComFuncionarios(id)
                .orElseThrow(() -> new RegraNegocioException(
                        "Projeto não encontrado."));

        return converterParaDTO(projeto);
    }

    @Override
    public void vincularFuncionario(
            Integer idProjeto, Integer idFuncionario) {

        Projeto projeto = projetoRepository
                .buscarProjetoComFuncionarios(idProjeto)
                .orElseThrow(() -> new RegraNegocioException(
                        "Projeto não encontrado."));

        Funcionario funcionario = funcionarioRepository
                .findById(idFuncionario)
                .orElseThrow(() -> new RegraNegocioException(
                        "Funcionário não encontrado."));

        for (Funcionario f : projeto.getFuncionarios()) {
            if (f.getId().equals(funcionario.getId())) {
                throw new RegraNegocioException(
                        "Funcionário já vinculado a esse projeto.");
            }
        }

        projeto.getFuncionarios().add(funcionario);

        projetoRepository.save(projeto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DadosProjetoDTO> listarTodos() {

        List<Projeto> projetos = projetoRepository.findAll();
        List<DadosProjetoDTO> resultado = new ArrayList<>();

        for (Projeto projeto : projetos) {
            resultado.add(converterParaDTO(projeto));
        }

        return resultado;
    }

    @Override
    @Transactional(readOnly = true)
    public List<DadosProjetoDTO> buscarPorPeriodo(
            LocalDate dataInicio, LocalDate dataFim) {

        if (dataInicio == null || dataFim == null) {
            throw new RegraNegocioException(
                    "Informe as datas inicial e final.");
        }

        if (dataFim.isBefore(dataInicio)) {
            throw new RegraNegocioException(
                    "A data final não pode ser anterior à data inicial.");
        }

        List<Projeto> projetos = projetoRepository.buscarPorPeriodo(dataInicio, dataFim);

        List<DadosProjetoDTO> resultado = new ArrayList<>();

        for (Projeto projeto : projetos) {
            resultado.add(converterParaDTO(projeto));
        }

        return resultado;
    }

    private DadosProjetoDTO converterParaDTO(Projeto projeto) {

        List<DadosFuncionarioDTO> funcionarios = new ArrayList<>();

        for (Funcionario funcionario : projeto.getFuncionarios()) {
            funcionarios.add(
                    new DadosFuncionarioDTO(
                            funcionario.getId(),
                            funcionario.getNome()));
        }

        return new DadosProjetoDTO(
                projeto.getId(),
                projeto.getDescricao(),
                projeto.getDataInicio(),
                projeto.getDataFim(),
                funcionarios);
    }
}