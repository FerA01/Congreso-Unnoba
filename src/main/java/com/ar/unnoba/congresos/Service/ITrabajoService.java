package com.ar.unnoba.congresos.Service;
import com.ar.unnoba.congresos.Model.Evento;
import com.ar.unnoba.congresos.Model.Trabajo;
import com.ar.unnoba.congresos.Model.Usuario;
import java.util.List;
import java.util.Optional;

public interface ITrabajoService {
    Optional<Trabajo> findById(Long id);
    boolean deleteById(Long id);
    boolean existeTrabajoEnEvento(Long id_evento, Long id_user);
    boolean existeTrabajoEnEvento(Long id_evento);
    Long findByUsuarioAndEvento(Long id_user, Long id_evento);
    Trabajo save2(Trabajo trabajo);
    List<Trabajo> findAllByUsuario(Usuario usuario);
    List<Trabajo> findAllByEvento(Evento evento);
    Long countByUsuario(Long id);
}
