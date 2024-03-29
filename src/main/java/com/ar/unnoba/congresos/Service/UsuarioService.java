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
import java.util.Optional;

@Service
public class UsuarioService implements IUsuarioService, UserDetailsService {
    @Autowired
    private UsuarioRepository repository;

    @Override
    public boolean create(Usuario usuario) {
        if (repository.findByEmail(usuario.getEmail())==null){ //se va a buscar al usuario por email en la bbdd, si no lo encuentra se va a guardar el usuario, caso contrario retorna false
            usuario.setPassword(new BCryptPasswordEncoder().encode(usuario.getPassword()));
            repository.save(usuario);
            return true;
        }
        return false;
    }

    @Override //Ordenados por nombre y apellido
    public List<Usuario> getAll() { return repository.findAll(Sort.by("nombre").ascending()
                                                        .and(Sort.by("apellido").ascending())
                                                      );
    }

    @Override
    public void delete(Long id) { repository.deleteById(id); }

    @Override
    public Optional<Usuario> findById(Long id) { return repository.findById(id); }

    @Override
    public void save2(Usuario usuario) { repository.save(usuario); }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        /*Usuario usuario = repository.findByEmail(email);
        String email2 = usuario.getEmail();
        if(email2 == null){
            throw new UsernameNotFoundException("User not authorized.");
        }
        GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_USER");
        UserDetails userDetails = (UserDetails)new User(email2,
                usuario.getPassword(), usuario.getAuthorities());
        return userDetails;

         */
        return repository.findByEmail(email);
    }
}
