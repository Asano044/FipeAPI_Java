package br.com.casasbahia.consultador.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DadosModelo (List<DadosVeiculo> modelos){}
