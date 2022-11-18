package com.ar.unnoba.congresos.Service;
import com.ar.unnoba.congresos.Model.Usuario;
import com.ar.unnoba.congresos.Repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UsuarioService implements IUsuarioService, UserDetailsService {
    @Autowired
    private UsuarioRepository repository;

    @Override
    public Usuario create(Usuario usuario) {
        if (getRepository().findByEmail(usuario.getEmail()) == null){
            usuario.setPassword(new BCryptPasswordEncoder().encode(usuario.getPassword()));
            usuario = repository.save(usuario);
        }
        return usuario;
    }

    @Override //Ordenados por nombre y apellido
    public List<Usuario> getAll() { return getRepository().findAll(Sort.by("nombre").ascending()
                                                          .and(Sort.by("apellido").ascending())
    );}

    @Override
    public void delete(Long id) { getRepository().deleteById(id); }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return (UserDetails) getRepository().findByEmail(email);
    }

    public UsuarioRepository getRepository() { return repository; }
}
