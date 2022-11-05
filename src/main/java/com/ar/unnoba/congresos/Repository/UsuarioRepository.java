package com.ar.unnoba.congresos.Repository;
import com.ar.unnoba.congresos.Model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    public Usuario findByEmail(String email);

}
