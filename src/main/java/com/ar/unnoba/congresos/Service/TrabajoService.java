package com.ar.unnoba.congresos.Service;
import com.ar.unnoba.congresos.Model.Trabajo;
import com.ar.unnoba.congresos.Model.Usuario;
import com.ar.unnoba.congresos.Repository.TrabajoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TrabajoService implements ITrabajoService {
    @Autowired
    private TrabajoRepository repository;

    @Override
    public Optional<Trabajo> findById(Long id) {
        Optional<Trabajo> trabajo = repository.findById(id);
        return (trabajo.isPresent()) ? trabajo : Optional.empty();
    }

    @Override
    public boolean existeTrabajoEnEvento(Long id_evento, Long id_user) {
        try {
            return repository.existeTrabajoEnEvento(id_evento, id_user) > 0L;
        }catch (Exception e){
            return false;
        }
    }

    @Override
    public boolean deleteById(Long id) {
        try {
            repository.deleteById(id);
            return true;
        }catch (Exception exception){
            return false;
        }
    }

    @Override
    public Long findByUsuarioAndEvento(Long id_user, Long id_evento) {
        return repository.findByUsuarioAndEvento(id_user, id_evento);
    }

    @Override
    public Trabajo save2(Trabajo trabajo) {
        return repository.save(trabajo);
    }

    @Override
    public List<Trabajo> findAllByUsuario(Usuario usuario) {
        if (usuario.getId() != null){
            return repository.findAllByUsuario(usuario);
        }
        return null;
    }

    @Override
    public Long countByUsuario(Long id) {
        if (id > 0){
            return repository.countByUsuario(id);
        }
        return -1L;
    }
}
