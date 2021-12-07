package br.com.gew.domain.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "secoes_pagantes")
public class SecaoPagante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "secao_id")
    private Secao secao;

    private double percentual;
    private double valor;

    @ManyToOne
    @JoinColumn(name = "projeto_id")
    private Projeto projeto;

}
