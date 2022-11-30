package com.ar.unnoba.congresos.Service;
import com.ar.unnoba.congresos.Model.Evento;
import com.ar.unnoba.congresos.Repository.EventoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventoService implements IEventoService{
    @Autowired
    private EventoRepository repository;


    @Override
    public Evento create(Evento evento) {
        if (!repository.existsById(evento.getId())){


            repository.save(evento);
        }
        return evento;
    }

    @Override
    public List<Evento> getAll() { return repository.findAll(
            Sort.by("nombre").ascending()
            .and(Sort.by("fechaHoraDesde").ascending())
    );
    }
    @Override
    public void delete(Long id) { repository.deleteById(id); }

    @Deprecated
    @Override
    public Evento getById(Long id) { return (id > 0) ? repository.getById(id) : null; }


    /**
     *  Si existe el evento con el id pasado por parametro
     *  devuelvo true si el evento tiene trabajos, caso contrario devuelve false
     * **/
    /*
    @Deprecated
    @Override
    public boolean hayTrabajos(Long id ) {
        Evento evento = getById(id);
        if (id > 0 && evento != null) {
            return !evento.getTrabajos().isEmpty();
        }
        return false;
    }

     */
}
