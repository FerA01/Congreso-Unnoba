package com.ar.unnoba.congresos.Service;
import com.ar.unnoba.congresos.Model.Organizador;
import com.ar.unnoba.congresos.Repository.OrganizadorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrganizadorService implements IOrganizadorService, UserDetailsService {
    @Autowired
    private OrganizadorRepository repository;

    @Override
    public Organizador create(Organizador organizador) {
        if (repository.findByEmail(organizador.getEmail()) == null){

        }
        return organizador;
    }

    @Override
    public List<Organizador> getAll() { return repository.findAll(); }

    @Override
    public void delete(Long id) { repository.deleteById(id); }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return repository.findByEmail(email);
    }
}
