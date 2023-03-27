package com.ar.unnoba.congresos.Service;
import com.ar.unnoba.congresos.Model.Trabajo;
import com.ar.unnoba.congresos.Repository.TrabajoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public Trabajo save2(Trabajo trabajo) {
        return repository.save(trabajo);
    }
}
