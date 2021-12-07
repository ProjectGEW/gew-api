package br.com.gew.domain.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "funcionarios")
public class Funcionario implements UserDetails {

    @Id
    private long numero_cracha;

    @Size(max = 200)
    private String nome;

    @Size(max = 12)
    private String cpf;

    @Size(max = 14)
    private String telefone;

    private double valor_hora;

    @Size(max = 255)
    private String email;

    @JsonIgnore
    @Size(max = 255)
    private String senha;

    @JsonIgnore
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "funcionarios_secoes", joinColumns = @JoinColumn(name = "funcionario_cracha", referencedColumnName = "numero_cracha"),
            inverseJoinColumns = @JoinColumn(name = "secao_id", referencedColumnName = "id"))
    private List<Secao> secoes;

    @JsonIgnore
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "cargos_funcionarios", joinColumns = @JoinColumn(name = "funcionario_cracha", referencedColumnName = "numero_cracha"),
            inverseJoinColumns = @JoinColumn(name = "cargo_id", referencedColumnName = "id"))
    private List<Cargo> cargos;

    @JsonIgnore
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "consultores_fornecedores", joinColumns = @JoinColumn(name = "funcionario_cracha", referencedColumnName = "numero_cracha"),
            inverseJoinColumns = @JoinColumn(name = "fornecedor_id", referencedColumnName = "id"))
    private List<Fornecedor> fornecedores;

    @JsonIgnore
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "consultores_skills", joinColumns = @JoinColumn(name = "funcionario_cracha", referencedColumnName = "numero_cracha"),
            inverseJoinColumns = @JoinColumn(name = "skill_id", referencedColumnName = "id"))
    private List<Skill> skills;

    @JsonIgnore
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return (Collection<? extends GrantedAuthority>) this.cargos;
    }

    @JsonIgnore
    @Override
    public String getPassword() {
        return this.senha;
    }

    @JsonIgnore
    @Override
    public String getUsername() {
        return this.email;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isEnabled() {
        return true;
    }

}
