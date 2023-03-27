package com.ar.unnoba.congresos.Service;
import com.ar.unnoba.congresos.Model.Trabajo;
import com.ar.unnoba.congresos.Repository.TrabajoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class TrabajoService implements ITrabajoService {
    @Autowired
    private TrabajoRepository repository;

    @Override
    public Trabajo save2(Trabajo trabajo) {
        return repository.save(trabajo);
    }
}
