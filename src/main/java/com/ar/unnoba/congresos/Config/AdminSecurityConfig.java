package com.ar.unnoba.congresos.Config;

import com.ar.unnoba.congresos.Service.OrganizadorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableWebSecurity
@EnableTransactionManagement
@EnableAutoConfiguration
@Primary
public class AdminSecurityConfig {
    @Autowired
    private OrganizadorService organizadorService;

    @Autowired
    public AdminSecurityConfig( OrganizadorService organizadorService){
        this.organizadorService = organizadorService;
    }


    @Bean
    public OrganizadorService userDetailService(){
        return new OrganizadorService();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http    .userDetailsService((UserDetailsService) organizadorService)
                .authorizeHttpRequests((requests) -> requests
                        .antMatchers("/webjars/**", "/resources/**", "/css/**").permitAll()
                        .antMatchers( "/admin/register").permitAll()
                        .antMatchers(HttpMethod.POST,"/admin/register/new").permitAll()
                        .antMatchers("/admin/eventos").hasAuthority("ROLE_ADMIN")
                        .antMatchers("/admin/eventos/new").hasRole("ROLE_ADMIN")
                        .antMatchers("/admin/eventos/**").hasRole("ROLE_ADMIN")
                        .anyRequest().authenticated()
                )
                .formLogin().loginPage("/login")
                .permitAll()
                .defaultSuccessUrl("/login", true)
                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .permitAll();
        return http.build();
    }
}
