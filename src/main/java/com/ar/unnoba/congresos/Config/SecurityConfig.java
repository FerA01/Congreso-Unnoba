package com.ar.unnoba.congresos.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig{
    @Autowired
    //@Qualifier("UsuarioService")
    private UserDetailsService userDetailsService;

    @Autowired
    public SecurityConfig(UserDetailsService userDetailsService){
        setUserDetailsService(userDetailsService);
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http //.authorizeRequests()
                //.antMatchers("/","/form/**","/css/**","/users/**").permitAll()
                //.antMatchers("/new/**").permitAll()
                //.antMatchers("/register/new/").permitAll()
                //.antMatchers("/users/**").permitAll()
                //.antMatchers("/register").permitAll()
                //.antMatchers("/index").permitAll()
               // .and()
                //.formLogin().loginPage("/login")
                //.permitAll()
                //.defaultSuccessUrl("/users", true
                .authorizeHttpRequests((requests) -> requests
                        .antMatchers("/webjars/**", "/resources/**", "/css/**").permitAll()
                        .antMatchers("/resources/**").permitAll()
                        .antMatchers("/","/login", "/register").permitAll()
                        .antMatchers(HttpMethod.POST,"/register/new").permitAll()
                        .antMatchers(HttpMethod.POST, "/delete/**").authenticated()
                        .anyRequest().authenticated()
                )
                .formLogin().loginPage("/login")
                .permitAll()
                .defaultSuccessUrl("/usuarios", true)
                .and()
                .logout().permitAll();
                /*
                .formLogin(
                        (form) -> form
                                .loginPage("/login")
                                .permitAll()
                                .defaultSuccessUrl("/users", true)
                )
                .logout((logout) -> logout.permitAll());

                 */
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

    public void setUserDetailsService(UserDetailsService userDetailsService) { this.userDetailsService = userDetailsService; }
}
