package com.ar.unnoba.congresos.Repository;
import com.ar.unnoba.congresos.Model.Trabajo;
import com.ar.unnoba.congresos.Model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface TrabajoRepository extends JpaRepository<Trabajo, Long> {
    List<Trabajo> findAllByUsuario(Usuario usuario);
    @Query("SELECT COUNT(id) FROM trabajos WHERE usuario_id = ?1")
    Long countByUsuario(@Param("id") Long id);
}
