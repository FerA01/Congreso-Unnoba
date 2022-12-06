package com.ar.unnoba.congresos.Service;

import com.ar.unnoba.congresos.Model.Organizador;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;


public interface IOrganizadorService {
    public boolean create(Organizador organizador);
    public List<Organizador> getAll();
    public void delete(Long id);
    public UserDetails loadUserByUsername(String email);
}
