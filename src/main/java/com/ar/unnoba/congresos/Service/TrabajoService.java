package com.ar.unnoba.congresos.Service;
import com.ar.unnoba.congresos.Model.Trabajo;
import com.ar.unnoba.congresos.Repository.TrabajoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TrabajoService implements ITrabajoService {
    @Autowired
    private TrabajoRepository repository;

    @Override
    public boolean create(Trabajo trabajo) {
        if (trabajo.getId() == null){
            repository.save(trabajo);
            return true;
        }
        return false;
    }

    @Override
    public List<Trabajo> getAll() { return repository.findAll(Sort.by("nombre").ascending()); }

    @Override
    public void delete(Long id) { repository.deleteById(id); }

    @Deprecated
    @Override
    public Trabajo getById(Long id) { return (id > 0) ? repository.getById(id) : null; }

    @Override
    public void save2(Trabajo trabajo) { repository.save(trabajo);}

    //@Override
    //public Trabajo obtenerTrabajo(Long idEvento) { return repository.getByIdEvento(idEvento); }
}
