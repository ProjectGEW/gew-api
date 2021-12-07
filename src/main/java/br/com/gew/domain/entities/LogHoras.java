package br.com.gew.domain.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "log_horas")
public class LogHoras {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private int horas;

    private String descricao;

    private LocalDate data;

    private LocalDateTime criado_em;

}
