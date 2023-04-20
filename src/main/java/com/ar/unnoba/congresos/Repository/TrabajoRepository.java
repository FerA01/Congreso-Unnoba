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
    @Query(value = "SELECT id FROM trabajos WHERE evento_id = ?2 AND usuario_id = ?1", nativeQuery = true)
    Long findByUsuarioAndEvento(@Param("id_user") Long id_user, @Param("id_evento") Long id_evento);
    @Query(value = "SELECT * FROM trabajos WHERE evento_id = ?1", nativeQuery = true)
    List<Trabajo> findAllByEvento(@Param("eventoId") Long eventoId);
    @Query(value = "SELECT * FROM trabajos WHERE usuario_id = ?1", nativeQuery = true)
    List<Trabajo> findAllByUsuario(@Param("id") Long id);
    @Query(value = "SELECT COUNT(id) FROM trabajos WHERE usuario_id = ?1", nativeQuery = true)
    Long countByUsuario(@Param("id") Long id);
    @Query(value = "SELECT COUNT(id) FROM trabajos WHERE evento_id = ?1 AND usuario_id = ?2", nativeQuery = true)
    Long existeTrabajoEnEvento(@Param("evento_id") Long evento_id, @Param("usuario_id") Long usuario_id);
    @Query(value = "SELECT COUNT(id) FROM trabajos WHERE evento_id = ?1", nativeQuery = true)
    Long existeTrabajoEnEvento(@Param("evento_id") Long evento_id);
}
