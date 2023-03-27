package com.ar.unnoba.congresos.Config;
import com.ar.unnoba.congresos.Error.CustomAccessDeniedHandler;
import com.ar.unnoba.congresos.Service.ServiceLogin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;

@Configuration
@EnableWebSecurity
@Order(2)
public class SecurityConfig{
    @Autowired
    //@Qualifier("UsuarioService")
    private ServiceLogin userDetailsService;

    @Autowired
    public SecurityConfig(ServiceLogin userDetailsService){
        this.userDetailsService = userDetailsService;
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http
                .authorizeHttpRequests((requests) -> requests
                        .antMatchers("/webjars/**", "/resources/**", "/css/**").permitAll()
                        .antMatchers("/resources/**").permitAll()
                        .antMatchers( "/usuarios/register").permitAll()
                        .antMatchers("/usuarios/**").permitAll()
                        .antMatchers(HttpMethod.POST,"/usuarios/register/new").permitAll()
                        .antMatchers("/admin/login").permitAll()
                        .anyRequest().authenticated()
                )
                .exceptionHandling().accessDeniedHandler(accessDeniedHandler())
                .and()
                .formLogin().loginPage("/login")
                .permitAll()
                .defaultSuccessUrl("/eventos", true)
                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .permitAll();
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
    @Bean
    public AccessDeniedHandler accessDeniedHandler(){ return new CustomAccessDeniedHandler(); }
}
