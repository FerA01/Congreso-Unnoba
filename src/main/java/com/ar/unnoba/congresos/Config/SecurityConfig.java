package com.ar.unnoba.congresos.Config;
import com.ar.unnoba.congresos.Service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig{
    @Autowired
    //@Qualifier("UsuarioService")
    private UsuarioService userDetailsService;

    @Autowired
    public SecurityConfig(UsuarioService userDetailsService){
        setUserDetailsService(userDetailsService);
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http
                .authorizeHttpRequests((requests) -> requests
                        .antMatchers("/webjars/**", "/resources/**", "/css/**").permitAll()
                        .antMatchers("/resources/**").permitAll()
                        .antMatchers( "/usuarios/register").permitAll()
                        .antMatchers(HttpMethod.POST,"/usuarios/register/new").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin().loginPage("/login")
                .permitAll()
                .defaultSuccessUrl("/usuarios", true)
                .and()
                .logout().permitAll();
        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() { return userDetailsService; }
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }

    public void setUserDetailsService(UsuarioService userDetailsService) { this.userDetailsService = userDetailsService; }
}
