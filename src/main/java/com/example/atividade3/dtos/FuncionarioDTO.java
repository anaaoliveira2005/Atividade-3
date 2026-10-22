package com.example.atividade3.dtos;

public class FuncionarioDTO {

    private String nome;
    private Integer setorId;

    public FuncionarioDTO() {
    }

    public FuncionarioDTO(String nome, Integer setorId) {
        this.nome = nome;
        this.setorId = setorId;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getSetorId() {
        return setorId;
    }

    public void setSetorId(Integer setorId) {
        this.setorId = setorId;
    }
}