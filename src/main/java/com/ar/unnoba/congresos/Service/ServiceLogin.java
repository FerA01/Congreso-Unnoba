package com.ar.unnoba.congresos.Service;

import com.ar.unnoba.congresos.Model.Organizador;
import com.ar.unnoba.congresos.Model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class ServiceLogin implements UserDetailsService {
    @Autowired
    private IOrganizadorService admin;

    @Autowired
    private IUsuarioService usuario;


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserDetails user = this.admin.loadUserByUsername(email);
        if(user == null){
            return (Usuario) this.usuario.loadUserByUsername(email);
        }else{
            return (Organizador) user;
        }
    }
}
