package br.com.gew.api.controller;

/*
 * Classe responsável por controlar a rota de
 * autenticação das credenciais
 * */

import br.com.gew.api.assembler.UsuarioAssembler;
import br.com.gew.api.model.input.UsuarioInputDTO;
import br.com.gew.api.model.output.AuthenticationResponse;
import br.com.gew.domain.entities.Funcionario;
import br.com.gew.domain.repositories.FuncionariosRepository;
import br.com.gew.security.ImplementsUserDetailsService;
import br.com.gew.security.JWTUtil;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
public class LoginController {

    private AuthenticationManager authenticationManager;
    private ImplementsUserDetailsService implementsUserDetailsService;
    private JWTUtil jwtUtil;
    private UsuarioAssembler usuarioAssembler;
    private FuncionariosRepository funcionariosRepositories;

    @PostMapping("/authenticate")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody UsuarioInputDTO usuarioInputDTO) throws Exception {
        Funcionario usuario = usuarioAssembler.toEntity(usuarioInputDTO);

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    usuario.getUsername(), usuario.getPassword()));
        } catch (BadCredentialsException ex) {
            throw new Exception("Usuário ou senha inválidos.", ex);
        }

        final UserDetails userDetails = implementsUserDetailsService.loadUserByUsername(usuario.getUsername());
        final String jwt = jwtUtil.generateToken(userDetails);
        Funcionario user = funcionariosRepositories.findByEmail(usuario.getEmail()).get();

        return ResponseEntity.ok(new AuthenticationResponse(jwt, usuarioAssembler.toModel(usuario), getName(user.getNumero_cracha())));
    }

    private String getName(Long id) {
        return funcionariosRepositories.findById(id).get().getNome();
    }
}
