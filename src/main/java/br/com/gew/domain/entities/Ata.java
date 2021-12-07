package br.com.gew.domain.entities;

/*
* Entidade para manter os dados da ata
*
* tipo: formato do arquivo
* data: bytes do arquivo
* */

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "atas")
public class Ata {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    private String nome;

    private String tipo;

    @Lob
    private byte[] data;

    private LocalDateTime criadoEm;

    @OneToOne
    @JoinColumn(name = "projeto_id")
    private Projeto projeto;

    public Ata(String nome, String tipo, byte[] data, LocalDateTime criadoEm, Projeto projeto) {
        this.nome = nome;
        this.tipo = tipo;
        this.data = data;
        this.criadoEm = criadoEm;
        this.projeto = projeto;
    }

    public Ata() {
    }
}
