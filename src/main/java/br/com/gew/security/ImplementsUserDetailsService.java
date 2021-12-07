package br.com.gew.security;

/*
* Classe para implementar as permissões de usuário
* */

import br.com.gew.domain.entities.Funcionario;
import br.com.gew.domain.exception.ExceptionTratement;
import br.com.gew.domain.repositories.FuncionariosRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
@Repository
@Transactional
public class ImplementsUserDetailsService implements UserDetailsService {

    private final FuncionariosRepository funcionarioRepositories;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        if (funcionarioRepositories.findByEmail(email).isEmpty()){
            throw new ExceptionTratement("User or Password invalid");
        }

        Funcionario usuario = funcionarioRepositories.findByEmail(email).get();

        return new User(
                usuario.getUsername(),
                usuario.getPassword(),
                true,
                true,
                true,
                true,
                usuario.getAuthorities()
        );
    }
}
