package com.example.atividade3.controllers;

import com.example.atividade3.dtos.ApiErrors;
import com.example.atividade3.exceptions.RegraNegocioException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApplicationControllerAdvice {

    @ExceptionHandler(RegraNegocioException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrors tratarRegraNegocio(RegraNegocioException ex) {

        return new ApiErrors(ex.getMessage());
    }
}