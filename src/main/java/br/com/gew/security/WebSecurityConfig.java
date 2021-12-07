package br.com.gew.security;

/*
* Configuração de segurança das rotas,
* dando permissões em determinadas rotas
* */

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@AllArgsConstructor
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter  {

    private final ImplementsUserDetailsService implementsUserDetailsService;
    private final JWTRequestFilter jwtRequestFilter;

    private static final String[] AUTH_LIST_FUNC = {
            "/",
            "/funcionarios",
            "/funcionarios/*",
            "/consultores",
            "/consultores/*",
            "/projetos",
            "/projetos/*",
            "/projetos/*/*",
            "/projetos/*/*/*",
            "/secoes/*/*",
            "/secoes/*",
            "/secoes",
            "/fornecedores",
            "/files/*/*"
    };


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/authenticate").permitAll()
                .antMatchers(HttpMethod.GET, AUTH_LIST_FUNC).permitAll()
                .antMatchers(HttpMethod.POST, AUTH_LIST_FUNC).permitAll()
                .antMatchers(HttpMethod.PUT, AUTH_LIST_FUNC).permitAll()
                .antMatchers(HttpMethod.DELETE, AUTH_LIST_FUNC).permitAll()
                .anyRequest().authenticated()
                .and().cors()
                .and().sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .deleteCookies("token").invalidateHttpSession(true);

        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(implementsUserDetailsService)
                .passwordEncoder(new BCryptPasswordEncoder());
    }


    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/botstrap/**", "/style/**");
    }

}
