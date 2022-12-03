package com.ar.unnoba.congresos.Service;
import com.ar.unnoba.congresos.Model.Trabajo;
import com.ar.unnoba.congresos.Repository.TrabajoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;

import java.util.List;

public class TrabajoService implements ITrabajoService {
    @Autowired
    private TrabajoRepository repository;

    @Override
    public Trabajo create(Trabajo trabajo) {
        if (!repository.existsById(trabajo.getId())){
            repository.save(trabajo);
        }
        return trabajo;
    }

    @Override
    public List<Trabajo> getAll() { return repository.findAll(
            Sort.by("nombre").ascending());
    }

    @Override
    public void delete(Long id) { repository.deleteById(id); }

    @Deprecated
    @Override
    public Trabajo getById(Long id) { return (id > 0) ? repository.getById(id) : null; }
}
