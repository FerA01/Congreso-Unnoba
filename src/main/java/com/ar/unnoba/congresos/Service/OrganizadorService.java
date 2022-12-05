package com.ar.unnoba.congresos.Service;
import com.ar.unnoba.congresos.Model.Organizador;
import com.ar.unnoba.congresos.Repository.OrganizadorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrganizadorService implements IOrganizadorService, UserDetailsService {
    @Autowired
    private OrganizadorRepository repository;

    @Override
    public boolean create(Organizador organizador) {
        if (repository.findByEmail(organizador.getEmail())==null){ //se va a buscar al usuario por email en la bbdd, si no lo encuentra se va a guardar el usuario, caso contrario retorna false
            organizador.setPassword(new BCryptPasswordEncoder().encode(organizador.getPassword()));
            repository.save(organizador);
            return true;
        }
        return false;
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
