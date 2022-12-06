package com.ar.unnoba.congresos.Config;
import com.ar.unnoba.congresos.Service.ServiceLogin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@Order(1)
public class AdminSecurityConfig {
    @Autowired
    private ServiceLogin organizadorService;

    @Autowired
    public AdminSecurityConfig( ServiceLogin organizadorService){
        this.organizadorService = organizadorService;
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http    .userDetailsService((UserDetailsService) organizadorService)
                .antMatcher("/admin/**")
                .authorizeHttpRequests((requests) -> requests
                        /*
                        .antMatchers("/webjars/**", "/resources/**", "/css/**").permitAll()
                        .antMatchers( "/admin/register").permitAll()
                        .antMatchers(HttpMethod.POST,"/admin/register/new").permitAll()
                        .antMatchers("/admin/eventos").hasAuthority("ROLE_ADMIN")
                        .antMatchers("/admin/eventos/new").hasRole("ROLE_ADMIN")
                        .antMatchers("/admin/eventos/**").hasRole("ROLE_ADMIN")
                        .antMatchers("/usuarios/**").permitAll()
                         */
                        .anyRequest().hasAuthority("ROLE_ADMIN")
                )
                .formLogin().loginPage("organizador/login")
                .loginProcessingUrl("/admin/login")
                .permitAll()
                .defaultSuccessUrl("/admin/eventos", true)
                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/admin/login")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .permitAll();
        return http.build();
    }
}
