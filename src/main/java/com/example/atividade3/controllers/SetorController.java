package com.example.atividade3.controllers;

import com.example.atividade3.dtos.SetorDTO;
import com.example.atividade3.dtos.DadosSetorDTO;
import com.example.atividade3.services.SetorService;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.ResponseStatus;

@RestController
@RequestMapping("/api/setores")
public class SetorController {

    private final SetorService setorService;

    public SetorController(SetorService setorService) {
        this.setorService = setorService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void adicionar(@RequestBody SetorDTO setor) {
        setorService.adicionar(setor);
    }

    @GetMapping("/{idSetor}")
    public DadosSetorDTO buscarSetorPorId(@PathVariable Integer idSetor) {
        return setorService.buscarSetorPorId(idSetor);
    }
}